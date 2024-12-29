package br.com.attus.contratomanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "contratos")
@Table(
        name = "contratos",
        indexes = {
                @Index(name = "idx_status", columnList = "status"),
                @Index(name = "idx_dataCriacao", columnList = "dataCriacao")
        }
)
@JsonPropertyOrder({"id", "numeroContrato", "dataCriacao", "descricao", "status", "pessoas", "eventos",})
@Data
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do contrato", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotBlank(message = "Número do contrato é obrigatório")
    @Column(unique = true)
    @Schema(description = "Número do contrato", example = "CNTR-123456")
    private String numeroContrato;

    @NotNull(message = "Data da criação é obrigatória")
    @Schema(description = "Data de criação do contrato", example = "2024-12-01")
    private LocalDate dataCriacao;

    @Schema(description = "Descrição do contrato", example = "Contrato de prestação de serviços", nullable = true)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do contrato", example = "ATIVO", allowableValues = {"ATIVO", "SUSPENSO", "ENCERRADO", "ARQUIVADO"})
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "contrato_pessoa",
            joinColumns = @JoinColumn(name = "contrato_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id")
    )
    @Schema(description = "Lista de pessoas associadas ao contrato", example = "[{\"id\": 1}, {\"id\": 2}]")
    private List<Pessoa> pessoas;

    @OneToMany(mappedBy = "contrato")
    @JsonIgnore
    @Schema(description = "Eventos relacionados ao contrato", example = "[{\"id\": 1}]")
    private List<Evento> eventos;

}
