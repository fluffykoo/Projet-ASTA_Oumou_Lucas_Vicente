package com.altn72.projetasta.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControleur {

    @GetMapping("/login")
    public String afficherLogin() {
        return "login"; // le nom du fichier login.html
    }
}