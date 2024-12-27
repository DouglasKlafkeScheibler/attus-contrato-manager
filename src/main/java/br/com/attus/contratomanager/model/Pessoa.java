package br.com.attus.contratomanager.model;

import br.com.attus.contratomanager.utils.CPFouCNPJ;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity(name = "pessoas")
@Table(name = "pessoas")
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @CPFouCNPJ
    @Column(unique = true)
    private String cpfCnpj;

    @NotBlank(message = "Contato é obrigatório")
    private String contato;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo da pessoa é obrigatório")
    private TipoPessoa tipoPessoa;

    @ManyToMany(mappedBy = "pessoas")
    @JsonIgnore
    private List<Contrato> contratos;
}
