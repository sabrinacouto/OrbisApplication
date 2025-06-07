package com.example.orbis_gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_oferta_doacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaDoacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oferta_id")
    private Long ofertaId;

    @NotBlank
    private String tipoDoacao;

    private String descricao;


    private Integer quantidade;

    private String localidade;

    private LocalDate dataOferta = LocalDate.now();

    private String status = "disponível";

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}

