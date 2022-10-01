package io.github.gustavoleitao.queryapi;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface OperatorStrategy {

    Predicate buildCriteria(Root<?> root, CriteriaBuilder criteria, String key, Comparable data);


}
