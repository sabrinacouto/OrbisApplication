package com.example.orbis_gs.controller;

import com.example.orbis_gs.config.GroqConfig;
import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmergenciaRecomendacaoController {

    private final GroqConfig groqConfig;
    private final UsuarioService usuarioService;

    @GetMapping("/emergencia")
    public String mostrarPaginaEmergencia(Model model,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String email = userDetails.getUsername();
            UsuarioDTO usuario = usuarioService.getUsuarioByEmail(email);
            model.addAttribute("usuario", usuario);
        }
        return "auth/emergencia";
    }

    @PostMapping("/recomendar")
    public String recomendarPlano(@RequestParam("desejo") String desejo,
                                  Model model,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        String resposta = groqConfig.gerarResposta(desejo);

        if (userDetails != null) {
            String email = userDetails.getUsername();
            UsuarioDTO usuario = usuarioService.getUsuarioByEmail(email);
            model.addAttribute("usuario", usuario);
        }

        model.addAttribute("recomendacao", resposta);
        return "auth/emergencia";
    }
}
