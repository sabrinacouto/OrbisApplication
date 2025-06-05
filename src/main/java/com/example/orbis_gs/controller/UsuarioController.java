package com.example.orbis_gs.controller;

import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "auth/form";
    }


    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, Model model) {
        try {
            usuarioService.createUsuario(usuarioDTO);
            return "redirect:/usuarios/login";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "auth/form";
        }
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "auth/login";
    }

    @GetMapping("/perfil/{id}")
    public String perfil(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getUsuarioById(id));
        return "user/profile";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuarioDTO", usuarioService.getUsuarioById(id));
        return "user/edit-user";
    }

    @PostMapping("/editar/{id}")
    public String atualizarUsuario(@PathVariable Long id, @ModelAttribute UsuarioDTO usuarioDTO) {
        usuarioService.updateUsuario(id, usuarioDTO);
        return "redirect:/usuarios/perfil/" + id;
    }
}