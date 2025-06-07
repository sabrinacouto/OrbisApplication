package com.example.orbis_gs.service;


import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.exceptions.EmailAlreadyExistsException;
import com.example.orbis_gs.model.Usuario;
import com.example.orbis_gs.producer.UsuarioProducer;
import com.example.orbis_gs.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock private BCryptPasswordEncoder passwordEncoder;
    @Mock private UsuarioRepository repository;
    @Mock private UsuarioProducer logProducer;
    @InjectMocks private UsuarioService service;
    private UsuarioDTO dto;



    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        dto = new UsuarioDTO();
        dto.setEmail("test@example.com");
        dto.setNome("Test");
        dto.setSobrenome("User");
        dto.setSenha("1234");
        dto.setCep("12345678");
    }

    @Test
    void createUsuario_existingEmail_throws() {
        when(repository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> service.createUsuario(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void createUsuario_newEmail_succeedsAndSendsLog() {
        when(repository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getSenha())).thenReturn("senhaCriptografada");

        Usuario saved = new Usuario();
        saved.setUsuarioId(1L);
        saved.setEmail(dto.getEmail());
        saved.setNome(dto.getNome());
        saved.setSobrenome(dto.getSobrenome());
        saved.setCep(dto.getCep());
        saved.setSenha("senhaCriptografada");

        when(repository.save(any())).thenReturn(saved);

        UsuarioDTO result = service.createUsuario(dto);

        assertEquals(1L, result.getUsuarioId());
        verify(logProducer).enviarLogCadastro(result);
    }

    @Test
    void authenticateUser_validCredentials_returnsUsuarioDTO() {
        String email = "test@example.com";
        String senha = "1234";
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setEmail(email);
        usuario.setNome("Test");
        usuario.setSobrenome("User");
        usuario.setSenha("hashed");

        when(repository.findByEmail(email)).thenReturn(java.util.Optional.of(usuario));
        when(passwordEncoder.matches(senha, "hashed")).thenReturn(true);

        UsuarioDTO result = service.authenticateUser(email, senha);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }


    @Test
    void authenticateUser_invalidCredentials_returnsNull() {
        String email = "test@example.com";
        String senha = "wrong";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("hashed");

        when(repository.findByEmail(email)).thenReturn(java.util.Optional.of(usuario));
        when(passwordEncoder.matches(senha, "hashed")).thenReturn(false);

        UsuarioDTO result = service.authenticateUser(email, senha);

        assertNull(result);
    }

    @Test
    void updateUsuario_existingUser_updatesFields() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(id);
        usuario.setEmail("old@example.com");
        usuario.setNome("Old");

        UsuarioDTO novoDTO = new UsuarioDTO();
        novoDTO.setEmail("new@example.com");
        novoDTO.setNome("New");
        novoDTO.setSobrenome("User");
        novoDTO.setCep("12345678");

        when(repository.findById(id)).thenReturn(java.util.Optional.of(usuario));
        when(repository.existsByEmail("new@example.com")).thenReturn(false);
        when(repository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        UsuarioDTO result = service.updateUsuario(id, novoDTO);

        assertEquals("new@example.com", result.getEmail());
        assertEquals("New", result.getNome());
        verify(repository).save(usuario);
    }


    @Test
    void deleteUsuario_notFound_throwsException() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deleteUsuario(id));
        verify(repository, never()).deleteById(any());
    }

}

