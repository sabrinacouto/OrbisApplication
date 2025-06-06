package com.example.orbis_gs.controller;

import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.exceptions.EmailAlreadyExistsException;
import com.example.orbis_gs.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String cadastrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
                                   Model model) {
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

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String email,
                               @RequestParam("password") String senha,
                               HttpSession session,
                               Model model) {

        UsuarioDTO usuario = usuarioService.authenticateUser(email, senha);

        if (usuario != null) {
            session.setAttribute("usuarioLogadoId", usuario.getUsuarioId());
            return "redirect:/auth/home";
        } else {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            return "auth/login";
        }
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        if (userDetails == null) return "redirect:/usuarios/login";

        String emailLogado = userDetails.getUsername();
        UsuarioDTO usuarioLogadoDTO = usuarioService.getUsuarioByEmail(emailLogado);
        if (usuarioLogadoDTO == null) return "redirect:/usuarios/login";

        if (!usuarioLogadoDTO.getUsuarioId().equals(id)) return "redirect:/home";

        UsuarioDTO usuarioParaEdicao = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuarioParaEdicao);
        return "user/edit-user";
    }


    @PatchMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id,
                                @ModelAttribute("usuario") UsuarioDTO usuarioDTO,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        if (userDetails == null) {
            return "redirect:/usuarios/login";
        }

        String emailLogado = userDetails.getUsername();
        UsuarioDTO usuarioLogadoDTO = usuarioService.getUsuarioByEmail(emailLogado);
        if (usuarioLogadoDTO == null) {
            return "redirect:/usuarios/login";
        }

        if (!usuarioLogadoDTO.getUsuarioId().equals(id)) {
            return "redirect:/home";
        }

        try {
            usuarioService.updateUsuario(id, usuarioDTO);
            model.addAttribute("usuario", usuarioService.getUsuarioById(id)); // Atualiza os dados do usuário no formulário
            model.addAttribute("success", "Perfil atualizado com sucesso!");
            return "user/edit-user";  // Volta para a mesma página mostrando a mensagem
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "user/edit-user";
        } catch (Exception e) {
            model.addAttribute("error", "Erro inesperado. Tente novamente mais tarde.");
            return "user/edit-user";
        }
    }


    @DeleteMapping("/deletarUsuario/{id}")
    public String deleteUsuario(@PathVariable("id") Long id,
                                @AuthenticationPrincipal UserDetails userDetails,
                                HttpSession session,
                                Model model) {

        if (userDetails == null) {
            model.addAttribute("error", "Usuário não logado.");
            return "redirect:/usuarios/login";
        }

        String emailLogado = userDetails.getUsername();
        UsuarioDTO usuarioLogadoDTO = usuarioService.getUsuarioByEmail(emailLogado);

        if (usuarioLogadoDTO == null || !usuarioLogadoDTO.getUsuarioId().equals(id)) {
            model.addAttribute("error", "Acesso negado.");
            return "redirect:/home";
        }

        usuarioService.deleteUsuario(id);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
