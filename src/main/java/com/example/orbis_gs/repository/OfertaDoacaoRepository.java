package com.example.orbis_gs.repository;

import com.example.orbis_gs.model.OfertaDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OfertaDoacaoRepository extends JpaRepository<OfertaDoacao, Long> {
    List<OfertaDoacao> findByTipoDoacao(String tipoDoacao);
}
