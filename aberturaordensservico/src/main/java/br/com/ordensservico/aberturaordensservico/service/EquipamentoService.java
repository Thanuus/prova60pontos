package br.com.ordensservico.aberturaordensservico.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import br.com.ordensservico.aberturaordensservico.model.Equipamento;
import br.com.ordensservico.aberturaordensservico.model.Setor;
import br.com.ordensservico.aberturaordensservico.repository.EquipamentoRepository;
import br.com.ordensservico.aberturaordensservico.repository.SetorRepository;

@Service
public class EquipamentoService {
    
    private final EquipamentoRepository equipamentoRepository;
    private final SetorRepository setorRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, SetorRepository setorRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.setorRepository = setorRepository;
    }

    public Optional<Equipamento> cadastrar(Equipamento equipamento) {
        if (equipamento.getSetor() == null) {
            return Optional.empty();
        }

        Optional<Setor> setorEncontrado = setorRepository.findById(equipamento.getSetor().getId());

        if (setorEncontrado.isEmpty()) {
            return Optional.empty();
        }

        equipamento.setSetor(setorEncontrado.get());
        return Optional.of(equipamentoRepository.save(equipamento));
    }

    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    public Optional<Equipamento> buscarPorId(int id) {
        return equipamentoRepository.findById(id);
    }

    public Optional<Equipamento> atualizar(
        Integer id, Equipamento novosDados) {
            
        Optional<Equipamento> equipamentoEncontrado = equipamentoRepository.findById(id);

        if (equipamentoEncontrado.isEmpty()) {
            return Optional.empty();
        }

        if (novosDados.getSetor() == null) {
            return Optional.empty();
        }

        Optional<Setor> setorEncontrado = setorRepository.findById(novosDados.getSetor().getId());

        if (setorEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Equipamento equipamento = equipamentoEncontrado.get();

        equipamento.setNome(novosDados.getNome());
        equipamento.setNumeroPatrimonio(novosDados.getNumeroPatrimonio());
        equipamento.setSetor(setorEncontrado.get());

        return Optional.of(equipamentoRepository.save(equipamento));
    }

    public boolean excluir(Integer id) {
        if (!equipamentoRepository.existsById(id)) {
            return false;
        }

        equipamentoRepository.deleteById(id);
        return true;
    }
}
