package br.com.attus.contratomanager.dto;

import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {
    @Schema(description = "ID do contrato", example = "1")
    private long id;

    @Schema(description = "Número do contrato", example = "CNTR-123456")
    private String numeroContrato;

    @Schema(description = "Data de criação do contrato", example = "2024-12-01")
    private LocalDate dataCriacao;

    @Schema(description = "Descrição do contrato", example = "Contrato de prestação de serviços", nullable = true)
    private String descricao;

    @Schema(description = "Status do contrato", example = "ATIVO")
    private Status status;

    @Schema(description = "Lista de pessoas associadas ao contrato")
    private List<Pessoa> pessoas;

    @Schema(description = "Lista de eventos relacionados ao contrato")
    private List<EventoDTO> eventos;

    private ContratoDTO(Builder builder) {
        this.id = builder.id;
        this.numeroContrato = builder.numeroContrato;
        this.dataCriacao = builder.dataCriacao;
        this.descricao = builder.descricao;
        this.status = builder.status;
        this.pessoas = builder.pessoas;
        this.eventos = builder.eventos;
    }

    public static class Builder {
        private long id;
        private String numeroContrato;
        private LocalDate dataCriacao;
        private String descricao;
        private Status status;
        private List<Pessoa> pessoas;
        private List<EventoDTO> eventos;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder numeroContrato(String numeroContrato) {
            this.numeroContrato = numeroContrato;
            return this;
        }

        public Builder dataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder pessoas(List<Pessoa> pessoas) {
            this.pessoas = pessoas;
            return this;
        }

        public Builder eventos(List<EventoDTO> eventos) {
            this.eventos = eventos;
            return this;
        }

        public ContratoDTO build() {
            return new ContratoDTO(this);
        }
    }
}