package com.school.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "admin/index";
    }
}