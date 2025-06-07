package com.example.orbis_gs.dto;


import com.example.orbis_gs.model.Usuario;

import lombok.Data;

import java.time.LocalDate;


@Data
public class PedidoAjudaDTO {

    private Long pedidoId;

    private String tipoAjuda;

    private String urgencia;

    private String descricao;

    private String localidade;

    private LocalDate dataPedido = LocalDate.now();

    private String status = "pendente";


    private Usuario usuario;
}

