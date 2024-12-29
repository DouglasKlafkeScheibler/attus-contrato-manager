package br.com.attus.contratomanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Representação simplificada do Contrato contendo apenas o ID", hidden = true)
public class ContratoIdDTO {
    @Schema(description = "ID do contrato", example = "1")
    private long id;
}