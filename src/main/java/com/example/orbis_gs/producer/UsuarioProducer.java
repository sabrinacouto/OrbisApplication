package com.example.orbis_gs.producer;


import com.example.orbis_gs.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioProducer {

    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public void enviarLogCadastro(UsuarioDTO usuarioDTO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, usuarioDTO);
        System.out.println("Log enviado : " + usuarioDTO);
    }
}

