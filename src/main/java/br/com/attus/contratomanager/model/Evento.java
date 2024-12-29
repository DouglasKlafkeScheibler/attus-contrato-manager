package br.com.attus.contratomanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "eventos")
@Table(name = "eventos")
@JsonPropertyOrder({"id", "tipoEvento", "dataRegistro", "descricao", "contrato"})
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do evento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotNull(message = "Tipo do evento é obrigatório")
    @Enumerated(EnumType.ORDINAL)
    @Schema(description = "Tipo do evento", example = "ASSINATURA", allowableValues = {"ASSINATURA", "RENOVAÇÃO", "RESCISÃO"})
    private TipoEvento tipoEvento;

    @NotNull(message = "Data do evento é obrigatória")
    @Schema(description = "Data de registro do evento", example = "2024-12-25")
    private LocalDate dataRegistro;

    @Schema(description = "Descrição detalhada do evento", example = "Assinatura do contrato", nullable = true)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    @NotNull(message = "Contrato é obrigatório")
    @Schema(description = "Contrato associado ao evento", example = "{\"id\": 1}")
    private Contrato contrato;
}
