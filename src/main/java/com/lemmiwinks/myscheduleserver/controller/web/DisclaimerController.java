package com.lemmiwinks.myscheduleserver.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DisclaimerController {
    @GetMapping("/disclaimer")
    public String privacyPolicy() {
        return "disclaimer";
    }
}
