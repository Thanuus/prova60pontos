package br.com.ordensservico.aberturaordensservico.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;


import br.com.ordensservico.aberturaordensservico.service.SetorService;
import br.com.ordensservico.aberturaordensservico.model.Setor;

@RestController
@RequestMapping("/setores")
public class OrdenServicosController {

    private final SetorService setorService;

    public OrdenServicosController(SetorService setorService) {
        this.setorService = setorService;
    }
    
    @PostMapping
    public ResponseEntity<Setor> cadastrar(
            @Valid @RequestBody Setor setor) {
            
        Setor setorCadastrado = setorService.cadastrar(setor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(setorCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Setor>> listar() {
        List<Setor> setor = setorService.listar();
        return ResponseEntity.ok(setor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setor> buscarPorId(
            @PathVariable int id) {

        Optional<Setor> setor = setorService.buscarPorId(id);
        
        if (setor.isPresent()) {
            return ResponseEntity.ok(setor.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setor> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Setor novosDados) {
        
        Optional<Setor> setorAtualizado = setorService.atualizar(id, novosDados);
        
        if (setorAtualizado.isPresent()) {
            return ResponseEntity.ok(setorAtualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {
        
        boolean excluido = setorService.excluir(id);
        
        if (excluido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

