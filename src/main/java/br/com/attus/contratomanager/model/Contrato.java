package br.com.attus.contratomanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "contratos")
@Table(name = "contratos")
@Data
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotBlank(message = "Numero do contrato é obrigatório")
    @Column(unique = true)
    private String numeroContrato;

    @NotNull(message = "Data da criação é obrigatório")
    private LocalDate dataCriacao;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "contrato_pessoa",
            joinColumns = @JoinColumn(name = "contrato_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id")
    )
    private List<Pessoa> pessoas;

    @OneToMany(mappedBy = "contrato")
    @JsonIgnore
    private List<Evento> eventos;

}
