package br.ufrn.lii.queryapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

public class QuerySpecification <T> implements Specification<T>  {

    private String filter = "";
    private Map<String,String> params;

    public enum OP {

        LIKE("$like", OperatorsImpl.like()),
        GT("$gt", OperatorsImpl.gt()),
        LT("$lt", OperatorsImpl.lt()),
        GE("$ge", OperatorsImpl.ge()),
        LE("$le", OperatorsImpl.le()),
        EQ("$eq", OperatorsImpl.eq());

        private OperatorStrategy strategy;

        private String operator;

        OP(String operator, OperatorStrategy strategy) {
            this.strategy = strategy;
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }

        public static OP byOperator(String name){
            return Arrays.stream(values())
                    .filter((item) -> item.getOperator().equals(name))
                    .limit(1)
                    .findAny().orElseThrow(() -> new IllegalArgumentException("Operador " + name + " não suportado"));
        }

        public OperatorStrategy getStrategy() {
            return strategy;
        }
    }

    public QuerySpecification(Map<String, String> params) {
        this.params = params;
    }

    public QuerySpecification(Map<String, String> params, String filter) {
        this.params = params;
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!params.containsKey("query")) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        String whereQuery = params.get("query");

        Map<String, Object> filterMap = parseJson(whereQuery);
        Map<String,Object> fixedFilter = parseJson(filter);

        Map<String,Object> filter = new HashMap<>(filterMap);
        filter.putAll(fixedFilter);

        if (filter.isEmpty()) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));

        Predicate[] predicates = generatePredicate(root, criteriaBuilder, filter);

        if (predicates.length == 0){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }else{
            return criteriaBuilder.and(predicates);
        }
    }

    private Predicate[] generatePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, Map<String, Object> filterMap) {
        var attrs = root.getModel().getDeclaredAttributes();

        Predicate[] predicates = filterMap.keySet().stream()
                .filter(key -> attrs.stream().anyMatch(item -> item.getName().equals(OperatorsImpl.getFieldByKey(key))))
                .map(key -> processCriteria(root, criteriaBuilder, key, filterMap.get(key)))
                .toArray(value -> new Predicate[value]);

        return predicates;
    }

    private Predicate processCriteria(Root<T> root, CriteriaBuilder criteria, String originalKey, Object data) {
        var attrt = root.getModel().getAttribute(OperatorsImpl.getFieldByKey(originalKey)).getJavaType();
        if (Map.class.isAssignableFrom(data.getClass())){
            Map<String, Object> mapData = (Map<String, Object>) data;
            return mapData.keySet()
                    .stream()
                    .limit(1)
                    .map(key -> OP.byOperator(key).getStrategy().buildCriteria(root, criteria, originalKey, ConverterUtil.toComparable(attrt, originalKey, mapData.get(key))))
                    .collect(Collectors.toList()).get(0);
        }else{
            return OP.EQ.getStrategy()
                    .buildCriteria(root,criteria, originalKey, ConverterUtil.toComparable(attrt, originalKey, data));
        }
    }

    private Map<String,Object> parseJson(String whereQuery) {
        try {
            return new ObjectMapper().readValue(whereQuery, HashMap.class);
        } catch (JsonProcessingException e) {
            return Collections.emptyMap();
        }
    }

}
