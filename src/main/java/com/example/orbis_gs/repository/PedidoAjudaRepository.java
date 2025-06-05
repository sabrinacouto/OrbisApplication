package com.example.orbis_gs.repository;


import com.example.orbis_gs.model.PedidoAjuda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoAjudaRepository extends JpaRepository<PedidoAjuda, Long> {
    List<PedidoAjuda> findByTipoAjuda(String tipoAjuda);
}