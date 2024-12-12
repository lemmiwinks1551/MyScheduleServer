package com.lemmiwinks.myscheduleserver.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyPolicyController {
    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }
}
