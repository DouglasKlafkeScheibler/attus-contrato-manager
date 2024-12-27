package br.com.attus.contratomanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "eventos")
@Table(name = "eventos")
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotNull(message = "Tipo do evento é obrigatório")
    private TipoEvento tipoEvento;

    @NotNull(message = "Data da criação é obrigatório")
    private LocalDate dataRegistro;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;
}
