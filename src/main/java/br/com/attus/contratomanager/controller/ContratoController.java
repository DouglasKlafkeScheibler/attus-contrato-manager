package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import br.com.attus.contratomanager.service.ContratoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Contrato")
public class ContratoController {
    @Autowired
    private ContratoService contratoService;

    @PostMapping("/contratos")
    ResponseEntity<Contrato> addContrato(@Valid @RequestBody Contrato contrato) {
        Contrato contratoCriado = contratoService.addContrato(contrato);
        return new ResponseEntity<>(contratoCriado, HttpStatus.CREATED);
    }

    @GetMapping("/contratos")
    ResponseEntity<List<Contrato>> findAll() {
        List<Contrato> contratoList = contratoService.findAll();
        return new ResponseEntity<>(contratoList, HttpStatus.OK);
    }

    @PutMapping("/contratos/{id}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable("id") long id, @Valid @RequestBody Contrato contratoBody) {
        Contrato contratoAtualizado = contratoService.updateContrato(id, contratoBody);
        return new ResponseEntity<>(contratoAtualizado, HttpStatus.OK);
    }

    @PostMapping("/arquivarContratos")
    ResponseEntity<Contrato> arquivarContrato(@Valid @RequestBody List<Long> contratoIds) {
        contratoService.arquivarContrato(contratoIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/contratos/filtro-paginado")
    public Page<ContratoDTO> buscarContratos(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) LocalDate dataCriacao,
            @RequestParam(required = false) String cpfCnpj,
            Pageable paginacao) {
        return contratoService.findContratosByStatusDataCriacaoCpfCnpjPageable(status, cpfCnpj, dataCriacao, paginacao);
    }


}
