package com.example.orbis_gs.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_match_ajuda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchAjuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    private LocalDateTime dataMatch = LocalDateTime.now();

    private String status = "pendente";

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoAjuda pedidoAjuda;

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private OfertaDoacao ofertaDoacao;
}
