package br.com.ordensservico.aberturaordensservico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = "equipamentos")
public class Equipamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    @NotNull(message = "O setor é obrigatório")
    private Setor setor;
    
    @NotBlank(message = "O nome do equipamento é obrigatório")
    private String nome;

    @NotBlank(message = "O número do patrimônio é obrigatório")
    private String numeroPatrimonio;

    public Equipamento() {
    }

    public Equipamento(String nome, String numeroPatrimonio, Setor setor) {
        this.nome = nome;
        this.numeroPatrimonio = numeroPatrimonio;
        this.setor = setor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroPatrimonio() {
        return numeroPatrimonio;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public void setNumeroPatrimonio(String numeroPatrimonio) {
        this.numeroPatrimonio = numeroPatrimonio;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}