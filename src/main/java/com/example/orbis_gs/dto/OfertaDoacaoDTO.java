package com.example.orbis_gs.dto;


import lombok.Data;

@Data
public class OfertaDoacaoDTO {
    private Long id;
    private String categoria;
    private String descricao;
    private String localidade;
    private Long usuarioId;
}
