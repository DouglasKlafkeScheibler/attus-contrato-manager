package br.com.attus.contratomanager.repository;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.dto.ContratoIdDTO;
import br.com.attus.contratomanager.dto.EventoDTO;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.QContrato;
import br.com.attus.contratomanager.model.QPessoa;
import br.com.attus.contratomanager.model.Status;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContratoRepositoryCustomImpl implements ContratoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ContratoRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ContratoDTO> findContratosByStatusDataCriacaoCpfCnpjPageable(Status status, String cpfCnpj, LocalDate dataCriacao, Pageable paginacao) {
        QContrato contrato = QContrato.contrato;
        QPessoa pessoa = QPessoa.pessoa;

        BooleanExpression predicate = montaPredicate(status, cpfCnpj, dataCriacao, contrato, pessoa);

        Long totalRegistros = queryFactory.select(contrato.id.countDistinct())
                .from(contrato)
                .innerJoin(pessoa).on(pessoa.in(contrato.pessoas))
                .where(predicate)
                .fetchOne();
        totalRegistros = totalRegistros != null ? totalRegistros : 0L;

        List<Contrato> contratos = queryFactory.selectFrom(contrato).distinct()
                .innerJoin(pessoa).on(pessoa.in(contrato.pessoas))
                .where(predicate)
                .offset(paginacao.getOffset())
                .limit(paginacao.getPageSize())
                .orderBy(contrato.id.asc())
                .fetch();

        List<ContratoDTO> contratoDTOs = contratos.stream()
                .map(this::mapToContratoDTO)
                .toList();

        return new PageImpl<>(contratoDTOs, paginacao, totalRegistros);
    }

    @Override
    public List<ContratoIdDTO> findContratosByCpfCnpj(String cpfCnpj) {
        QContrato contrato = QContrato.contrato;
        QPessoa pessoa = QPessoa.pessoa;

        return queryFactory
                .select(Projections.bean(ContratoIdDTO.class, contrato.id))
                .from(contrato)
                .innerJoin(pessoa).on(pessoa.in(contrato.pessoas))
                .where(pessoa.cpfCnpj.eq(cpfCnpj))
                .orderBy(contrato.id.asc())
                .fetch();
    }

    private BooleanExpression montaPredicate(Status status, String cpfCnpj, LocalDate dataCriacao, QContrato contrato, QPessoa pessoa) {
        BooleanExpression predicate = contrato.isNotNull();

        if (status != null) {
            predicate = predicate.and(contrato.status.eq(status));
        }

        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            predicate = predicate.and(pessoa.cpfCnpj.eq(cpfCnpj));
        }

        if (dataCriacao != null) {
            predicate = predicate.and(contrato.dataCriacao.eq(dataCriacao));
        }

        return predicate;
    }

    private ContratoDTO mapToContratoDTO(Contrato contratoEntity) {
        List<EventoDTO> eventoDTOs = contratoEntity.getEventos().stream()
                .map(eventoEntity -> new EventoDTO.Builder()
                        .id(eventoEntity.getId())
                        .tipoEvento(eventoEntity.getTipoEvento())
                        .dataRegistro(eventoEntity.getDataRegistro())
                        .descricao(eventoEntity.getDescricao())
                        .build())
                .collect(Collectors.toList());

        return new ContratoDTO.Builder()
                .id(contratoEntity.getId())
                .numeroContrato(contratoEntity.getNumeroContrato())
                .dataCriacao(contratoEntity.getDataCriacao())
                .descricao(contratoEntity.getDescricao())
                .status(contratoEntity.getStatus())
                .pessoas(contratoEntity.getPessoas())
                .eventos(eventoDTOs)
                .build();
    }
}