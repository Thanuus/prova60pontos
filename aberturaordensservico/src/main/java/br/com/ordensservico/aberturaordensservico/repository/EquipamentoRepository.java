package br.com.ordensservico.aberturaordensservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ordensservico.aberturaordensservico.model.Equipamento;

public interface EquipamentoRepository 
        extends JpaRepository<Equipamento, Integer> {
    
}