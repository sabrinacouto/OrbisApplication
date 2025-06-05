package com.example.orbis_gs.config;

import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UsuarioInterceptor implements HandlerInterceptor {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogadoId") != null) {
            Long id = (Long) session.getAttribute("usuarioLogadoId");
            UsuarioDTO usuario = usuarioService.getUsuarioById(id);
            request.setAttribute("usuario", usuario);
        }
        return true;
    }
}
