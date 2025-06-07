package com.example.orbis_gs.repository;

import com.example.orbis_gs.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario criarUsuarioPadrao() {
        Usuario usuario = new Usuario();
        usuario.setNome("Sabrina");
        usuario.setSobrenome("Silva");
        usuario.setEmail("sabrina@email.com");
        usuario.setSenha("123456");
        usuario.setCep("12345678");
        return usuario;
    }

    @Test
    public void testSalvarUsuario() {
        Usuario usuario = criarUsuarioPadrao();
        Usuario saved = usuarioRepository.save(usuario);

        Assertions.assertNotNull(saved.getUsuarioId());
        Assertions.assertEquals("Sabrina", saved.getNome());
        Assertions.assertEquals("Silva", saved.getSobrenome());
        Assertions.assertEquals("sabrina@email.com", saved.getEmail());
    }

    @Test
    public void testBuscarPorId() {
        Usuario usuario = usuarioRepository.save(criarUsuarioPadrao());
        Optional<Usuario> found = usuarioRepository.findById(usuario.getUsuarioId());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("Sabrina", found.get().getNome());
    }

    @Test
    public void testAtualizarUsuario() {
        Usuario usuario = usuarioRepository.save(criarUsuarioPadrao());
        usuario.setNome("Sabrina Atualizada");
        Usuario updated = usuarioRepository.save(usuario);

        Assertions.assertEquals("Sabrina Atualizada", updated.getNome());
    }

    @Test
    public void testDeletarUsuario() {
        Usuario usuario = usuarioRepository.save(criarUsuarioPadrao());
        Long id = usuario.getUsuarioId();

        usuarioRepository.deleteById(id);
        Optional<Usuario> deleted = usuarioRepository.findById(id);

        Assertions.assertFalse(deleted.isPresent());
    }
}
