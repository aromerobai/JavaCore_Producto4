package org.javacoreuocx.alquilatusvehiculos.controller;

import org.javacoreuocx.alquilatusvehiculos.model.Cliente;
import org.javacoreuocx.alquilatusvehiculos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublicController {
    @Autowired
    private ClienteRepository userRepo;

    @GetMapping("")
    public String homeNotLoggedIn() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Cliente cliente = new Cliente();
        if(cliente.getRol() == null) {
            cliente.setRol("USER");
        }
        model.addAttribute("user", cliente);
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(Cliente user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "login";
    }

}
