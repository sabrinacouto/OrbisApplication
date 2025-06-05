package com.example.orbis_gs.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Double latitude;
    private Double longitude;
    private String cep;
}
