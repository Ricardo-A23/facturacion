package org.curso.data.jpa.ejemplo.springjpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Principal principal, Model model, RedirectAttributes mensaje){
        //el principal obtiene el usuario actual si ya esta autenticado

        if(principal != null){
            mensaje.addFlashAttribute("info", "Ya tiene una sesión abierta: " + principal.getName());
            return "redirect:/";
        }

        if(error != null){
            model.addAttribute("error", "Usuario o contraseña incorrecta");
        }

        if(logout != null){
            model.addAttribute("success", "Ha cerrado sesión con exito");
        }

        return "login";
    }
}
