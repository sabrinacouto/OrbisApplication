package com.example.orbis_gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_pedido_ajuda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAjuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoId;

    @NotBlank
    private String tipoAjuda;

    @NotBlank
    private String urgencia;

    private String descricao;

    private String localidade;

    private LocalDate dataPedido = LocalDate.now();

    private String status = "pendente";

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
