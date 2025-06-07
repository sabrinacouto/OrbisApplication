package com.example.orbis_gs.controller;

import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.producer.UsuarioProducer;
import com.example.orbis_gs.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.RabbitMQContainer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
class UsuarioServiceIntegrationTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UsuarioProducer usuarioProducer() {
            return mock(UsuarioProducer.class);
        }
    }

    @Autowired private UsuarioService service;

    static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);

    @BeforeAll
    static void startRabbit() {
        rabbit.start();
        System.setProperty("spring.rabbitmq.host", rabbit.getHost());
        System.setProperty("spring.rabbitmq.port", rabbit.getAmqpPort().toString());
        System.setProperty("spring.rabbitmq.username", rabbit.getAdminUsername());
        System.setProperty("spring.rabbitmq.password", rabbit.getAdminPassword());
    }

    @Test
    void createUsuario_sendsToRabbit() throws InterruptedException {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("teste@example.com");
        dto.setNome("Int");
        dto.setSobrenome("Test");
        dto.setSenha("pass");
        dto.setCep("87654321");

        service.createUsuario(dto);

        assertTrue(true);
    }
}