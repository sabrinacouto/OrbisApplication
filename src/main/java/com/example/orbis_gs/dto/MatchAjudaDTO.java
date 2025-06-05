package com.example.orbis_gs.dto;

import com.example.orbis_gs.model.OfertaDoacao;
import com.example.orbis_gs.model.PedidoAjuda;
import lombok.Data;
import java.time.LocalDateTime;


@Data

public class MatchAjudaDTO {

    private Long matchId;

    private LocalDateTime dataMatch = LocalDateTime.now();

    private String status = "pendente";

    private PedidoAjuda pedidoAjuda;

    private OfertaDoacao ofertaDoacao;
}

