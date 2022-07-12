package br.ufrn.lii.genericapi.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface OperatorStrategy {

    Predicate buildCriteria(Root<?> root, CriteriaBuilder criteria, String key, String data);

}