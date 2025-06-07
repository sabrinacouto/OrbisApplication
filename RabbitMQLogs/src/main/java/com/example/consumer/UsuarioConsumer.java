package com.example.consumer;

import com.example.dto.UsuarioDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConsumer {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receberLogCadastro(UsuarioDTO usuario) {
        if (usuario == null) {
            System.err.println("Mensagem recebida é nula.");
            return;
        }

        System.out.println("📥 Usuário recebido via RabbitMQ:");
        System.out.println("   Nome: " + usuario.getNome());
        System.out.println("   Email: " + usuario.getEmail());
        System.out.println("   ID: " + usuario.getUsuarioId());
    }
}
