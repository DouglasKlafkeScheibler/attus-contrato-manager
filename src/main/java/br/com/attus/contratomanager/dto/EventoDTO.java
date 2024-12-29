package br.com.attus.contratomanager.dto;

import br.com.attus.contratomanager.model.TipoEvento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventoDTO {
    @Schema(description = "ID único do evento", example = "1")
    private long id;

    @Schema(description = "Tipo do evento", example = "ASSINATURA", allowableValues = {"ASSINATURA", "RENOVAÇÃO", "RESCISÃO"})
    private TipoEvento tipoEvento;

    @Schema(description = "Data de registro do evento", example = "2024-12-25")
    private LocalDate dataRegistro;

    @Schema(description = "Descrição detalhada do evento", example = "Assinatura do contrato", nullable = true)
    private String descricao;

    private EventoDTO(Builder builder) {
        this.id = builder.id;
        this.tipoEvento = builder.tipoEvento;
        this.dataRegistro = builder.dataRegistro;
        this.descricao = builder.descricao;
    }

    public static class Builder {
        private long id;
        private TipoEvento tipoEvento;
        private LocalDate dataRegistro;
        private String descricao;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder tipoEvento(TipoEvento tipoEvento) {
            this.tipoEvento = tipoEvento;
            return this;
        }

        public Builder dataRegistro(LocalDate dataRegistro) {
            this.dataRegistro = dataRegistro;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public EventoDTO build() {
            return new EventoDTO(this);
        }
    }
}