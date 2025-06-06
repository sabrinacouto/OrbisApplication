package com.example.orbis_gs.service;

import com.example.orbis_gs.dto.UsuarioDTO;
import com.example.orbis_gs.exceptions.EmailAlreadyExistsException;
import com.example.orbis_gs.exceptions.ResourceNotFoundException;
import com.example.orbis_gs.model.Usuario;
import com.example.orbis_gs.producer.UsuarioProducer;
import com.example.orbis_gs.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioProducer logProducer;

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNome(usuario.getNome());
        dto.setSobrenome(usuario.getSobrenome());
        dto.setCep(usuario.getCep());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    @Transactional
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este e-mail.");
        }

        Usuario entity = new Usuario();
        entity.setNome(usuarioDTO.getNome());
        entity.setSobrenome(usuarioDTO.getSobrenome());
        entity.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        entity.setCep(usuarioDTO.getCep());
        entity.setEmail(usuarioDTO.getEmail());
        usuarioRepository.save(entity);


        UsuarioDTO salvoDTO = convertToDTO(entity);

        logProducer.enviarLogCadastro(salvoDTO);

        return salvoDTO;
    }

    @Transactional
    public void updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        if (!usuario.getEmail().equals(usuarioDTO.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Já existe um usuário com este e-mail.");
        }

        if (usuarioDTO.getEmail() != null) {
            usuario.setEmail(usuarioDTO.getEmail());
        }
        if (usuarioDTO.getNome() != null) usuario.setNome(usuarioDTO.getNome());
        if (usuarioDTO.getSobrenome() != null) usuario.setSobrenome(usuarioDTO.getSobrenome());
        if (usuarioDTO.getCep() != null) usuario.setCep(usuarioDTO.getCep());
        if (usuarioDTO.getSenha() != null) usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));


        System.out.println("Atualizando usuário ID: " + id);
        System.out.println("Novo nome: " + usuarioDTO.getNome());
        System.out.println("Novo sobrenome: " + usuarioDTO.getSobrenome());
        System.out.println("Novo email: " + usuarioDTO.getEmail());
        System.out.println("Novo cep: " + usuarioDTO.getCep());



        usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return convertToDTO(usuario);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        usuarioRepository.delete(usuario);
    }

    public UsuarioDTO authenticateUser(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent() && passwordEncoder.matches(senha, usuarioOpt.get().getSenha())) {
            Usuario usuario = usuarioOpt.get();
            UsuarioDTO dto = new UsuarioDTO();
            dto.setUsuarioId(usuario.getUsuarioId()); // <-- isso é essencial!
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setSobrenome(usuario.getSobrenome());
            dto.setCep(usuario.getCep());
            return dto;
        }
        return null;
    }

    public UsuarioDTO getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com e-mail: " + email));
        return convertToDTO(usuario);
    }

}