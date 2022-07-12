package br.ufrn.lii.genericapi.repository;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class GenericSpecification<T> implements Specification<T> {

    private Map<String,String> params;

    public GenericSpecification(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        var attrs = root.getModel().getDeclaredAttributes();

        Predicate[] predicates = params.keySet().stream()
                .filter(key -> attrs.stream().anyMatch(item -> item.getName().equals(key)))
                .map(key -> criteriaBuilder.equal(root.get(key), params.get(key)))
                .toArray(value -> new Predicate[value]);

        if (predicates.length <= 0){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }else{
            return criteriaBuilder.and(predicates);
        }

    }

}
