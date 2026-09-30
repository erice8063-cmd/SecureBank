package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.User;
import org.example.dbproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            User user,
            Model model
    ) {
        try {
            userService.save(user);

            return "redirect:/login?registered";

        } catch (IllegalArgumentException exception) {
            user.setPassword(null);

            model.addAttribute("user", user);
            model.addAttribute(
                    "error",
                    exception.getMessage()
            );

            return "register";
        }
    }
}