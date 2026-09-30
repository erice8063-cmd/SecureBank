package org.example.dbproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerAccountController {

    @GetMapping("/customer-account")
    public String customerAccount() {
        return "customer-account";
    }
}