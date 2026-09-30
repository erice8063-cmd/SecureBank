package org.example.dbproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}