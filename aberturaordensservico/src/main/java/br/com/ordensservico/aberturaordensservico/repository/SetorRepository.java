package br.com.ordensservico.aberturaordensservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ordensservico.aberturaordensservico.model.Setor;

public interface SetorRepository 
        extends JpaRepository<Setor, Integer> {
    
}