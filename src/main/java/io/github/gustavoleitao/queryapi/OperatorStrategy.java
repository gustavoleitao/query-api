package io.github.gustavoleitao.queryapi;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface OperatorStrategy {

    Predicate buildCriteria(Root<?> root, CriteriaBuilder criteria, String key, Comparable data);


}
