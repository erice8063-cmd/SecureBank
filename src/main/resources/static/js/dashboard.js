const DASHBOARD_ENDPOINTS = {
    CUSTOMER: "/api/customer/dashboard",
    TELLER: "/api/teller/dashboard",
    ADMIN: "/api/admin/dashboard"
};

async function loadDashboard() {
    const role = document.body.dataset.role;
    const endpoint = DASHBOARD_ENDPOINTS[role];

    if (!endpoint) {
        showDashboardError("Unknown dashboard role.");
        return;
    }

    try {
        const response = await fetch(
            endpoint,
            {
                method: "GET",

                /*
                 * Sends the Spring Security JSESSIONID
                 * cookie to the backend.
                 */
                credentials: "include",

                headers: {
                    "Accept": "application/json"
                }
            }
        );

        if (response.status === 401) {
            window.location.href = "/login";
            return;
        }

        /*
         * The user may be authenticated but viewing a dashboard
         * that does not match their role.
         */
        if (response.status === 403) {
            await redirectFromForbiddenDashboard(role);
            return;
        }

        if (!response.ok) {
            throw new Error(
                `Dashboard request failed: ${response.status}`
            );
        }

        const dashboard = await response.json();

        /*
         * Prevent a user from displaying the wrong dashboard
         * HTML manually.
         */
        if (dashboard.role !== role) {
            redirectToCorrectDashboard(dashboard.role);
            return;
        }

        displayUser(dashboard);
        displayStatistics(dashboard.statistics);
        displayMessage(dashboard.message);

    } catch (error) {
        console.error(error);

        showDashboardError(
            "SecureBank could not load dashboard information."
        );
    }
}

/*
 * Handles a 403 from a role-specific dashboard endpoint.
 *
 * For example, if a customer opens /admin-dashboard,
 * /api/admin/dashboard returns 403. The application then
 * checks the current user's actual role and redirects them
 * to the correct dashboard.
 */
async function redirectFromForbiddenDashboard(requestedRole) {
    try {
        const response = await fetch(
            "/api/users/me",
            {
                method: "GET",

                /*
                 * Include the current JSESSIONID cookie.
                 */
                credentials: "include",

                headers: {
                    "Accept": "application/json"
                }
            }
        );

        const contentType =
            response.headers.get("content-type") || "";

        /*
         * An expired session may be redirected to the HTML
         * login page instead of returning JSON.
         */
        if (
            !response.ok ||
            !contentType.includes("application/json")
        ) {
            window.location.href = "/login";
            return;
        }

        const currentUser = await response.json();

        /*
         * Redirect only when the authenticated user's role
         * differs from the dashboard that was requested.
         */
        if (
            currentUser.role &&
            currentUser.role !== requestedRole
        ) {
            redirectToCorrectDashboard(currentUser.role);
            return;
        }

        /*
         * If the user's role matches but access is still denied,
         * this is a genuine authorization problem.
         */
        window.location.href = "/access-denied";

    } catch (error) {
        console.error(error);

        /*
         * If the current session cannot be checked, require
         * the user to sign in again.
         */
        window.location.href = "/login";
    }
}

function displayUser(dashboard) {
    setText(
        "dashboardUserName",
        dashboard.name ||
        dashboard.username ||
        "User"
    );

    setText(
        "dashboardUserEmail",
        dashboard.email || ""
    );

    setText(
        "dashboardUserInitial",
        getInitial(
            dashboard.name ||
            dashboard.username
        )
    );
}

function displayMessage(message) {
    setText(
        "dashboardMessage",
        message || "Dashboard ready."
    );
}

function displayStatistics(statistics) {
    if (!statistics) {
        return;
    }

    Object.entries(statistics).forEach(
        ([name, value]) => {
            setText(name, value);
        }
    );
}

function setText(elementId, value) {
    const element =
        document.getElementById(elementId);

    if (element) {
        element.textContent = value;
    }
}

function getInitial(value) {
    if (!value || value.trim().length === 0) {
        return "U";
    }

    return value
        .trim()
        .charAt(0)
        .toUpperCase();
}

function redirectToCorrectDashboard(role) {
    const dashboardPages = {
        CUSTOMER: "/customer-dashboard",
        TELLER: "/teller-dashboard",
        ADMIN: "/admin-dashboard"
    };

    const page = dashboardPages[role];

    if (page) {
        window.location.href = page;
        return;
    }

    window.location.href = "/access-denied";
}

function showDashboardError(message) {
    const errorElement =
        document.getElementById("dashboardError");

    if (errorElement) {
        errorElement.textContent = message;
        errorElement.hidden = false;
    }
}

loadDashboard();