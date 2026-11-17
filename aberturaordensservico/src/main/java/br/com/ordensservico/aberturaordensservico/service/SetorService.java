package br.com.ordensservico.aberturaordensservico.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import br.com.ordensservico.aberturaordensservico.model.Setor;
import br.com.ordensservico.aberturaordensservico.repository.SetorRepository;


@Service
public class SetorService {
    
    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public Setor cadastrar(Setor setor) {
        return setorRepository.save(setor);
    }

    public List<Setor> listar() {
        return setorRepository.findAll();
    }

    public Optional<Setor> buscarPorId(int id) {
        return setorRepository.findById(id);
    }

    public Optional<Setor> atualizar(
        Integer id, Setor novosDados) {
            
        Optional<Setor> setorEncontrado = setorRepository.findById(id);

        if (setorEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Setor setor = setorEncontrado.get();

        setor.setNome(novosDados.getNome());

        return Optional.of(setorRepository.save(setor));
    }

    public boolean excluir(Integer id) {
        if (!setorRepository.existsById(id)) {
            return false;
        }

        setorRepository.deleteById(id);
        return true;
    }
}