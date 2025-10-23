package com.altn72.projetasta.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardControleur {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}