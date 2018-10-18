package com.example.banner.controller;

import com.example.banner.domain.Role;
import com.example.banner.domain.User;
import com.example.banner.repos.UserRepo;
import com.example.banner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class Sign_UpController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/sign_up")
    public String sign_up() {

        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (user.getPassword() == null) {
            model.addAttribute("passwordError", "Password couldn't be empty");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "sign_up";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usenameError", "User exixts!");
            return "sign_up";
        }
        return "redirect:/login";
    }
}

