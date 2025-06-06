package com.example.orbis_gs.config;

import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UsuarioInterceptor implements HandlerInterceptor {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Apenas permitir continuar a requisição
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Long id = (Long) session.getAttribute("usuarioLogadoId");
                if (id != null) {
                    UsuarioDTO usuario = usuarioService.getUsuarioById(id);
                    modelAndView.addObject("usuario", usuario);
                }
            }
        }
    }
}
