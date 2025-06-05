package com.example.orbis_gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tb_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String sobrenome;

    @NotBlank
    private String senha;

    private Double latitude;
    private Double longitude;

    private String cep;
}
