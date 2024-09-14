package com.lemmiwinks.myscheduleserver.controller;

import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (userRepository.existsByUsername(userForm.getUsername())){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        if (userRepository.existsByUserEmail(userForm.getUserEmail())){
            model.addAttribute("userEmailError", "Пользователь с таким Email уже существует");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            registerUser(userForm);
        }

        return "redirect:/login";
    }

    public void registerUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }
}