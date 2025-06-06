package com.example.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long usuarioId;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O sobrenome é obrigatório.")
    private String sobrenome;

    @Email(message = "O email deve ser válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @NotBlank(message = "O CEP é obrigatório.")
    @Size(min = 8, max = 9, message = "O CEP deve ter 8 ou 9 caracteres.")
    private String cep;
}
