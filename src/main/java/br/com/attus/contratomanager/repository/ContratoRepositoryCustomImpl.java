package br.com.attus.contratomanager.repository;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.QContrato;
import br.com.attus.contratomanager.model.QPessoa;
import br.com.attus.contratomanager.model.Status;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ContratoRepositoryCustomImpl implements ContratoRepositoryCustom {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ContratoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Contrato> findContratosByStatusDataCriacaoCpfCnpjPageable(Status status, String cpfCnpj, LocalDate dataCriacao, Pageable paginacao) {
        QContrato contrato = QContrato.contrato;
        QPessoa pessoa = QPessoa.pessoa;

        BooleanExpression predicate = montaPredicate(status, cpfCnpj, dataCriacao, contrato, pessoa);

        JPAQuery<Long> countQuery = queryFactory.select(contrato.count())
                .from(contrato)
                .leftJoin(contrato.pessoas, pessoa)
                .where(predicate);
        Long totalRegistros = countQuery.fetchOne();
        totalRegistros = totalRegistros != null ? totalRegistros : 0L;

        JPAQuery<Contrato> query = queryFactory.selectFrom(contrato)
                .leftJoin(contrato.pessoas, pessoa)
                .where(predicate);

        List<Contrato> contratos = query.offset(paginacao.getOffset())
                .limit(paginacao.getPageSize())
                .fetch();

        return new PageImpl<>(contratos, paginacao, totalRegistros);
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
}