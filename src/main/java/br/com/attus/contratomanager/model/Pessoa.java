package br.com.attus.contratomanager.model;

import br.com.attus.contratomanager.utils.CPFouCNPJ;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity(name = "pessoas")
@Table(
        name = "pessoas",
        indexes = @Index(name = "idx_cpfCnpj", columnList = "cpfCnpj")
)
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da pessoa", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo da pessoa", example = "Douglas Klafke")
    private String nome;

    @CPFouCNPJ
    @Column(unique = true)
    @Schema(description = "Número do CPF ou CNPJ da pessoa", example = "47245058008")
    private String cpfCnpj;

    @NotBlank(message = "Contato é obrigatório")
    @Schema(description = "Contato da pessoa (e-mail ou telefone)", example = "Douglas@gmail.com")
    private String contato;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo da pessoa é obrigatório")
    @Schema(description = "Tipo da pessoa (FORNECEDOR, CLIENTE, ADVOGADO)", example = "CLIENTE", allowableValues = {"FORNECEDOR", "CLIENTE", "ADVOGADO"})
    private TipoPessoa tipoPessoa;

    @ManyToMany(mappedBy = "pessoas")
    @JsonIgnore
    private List<Contrato> contratos;
}
