package br.ufrn.lii.genericapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QuerySpecification <T> implements Specification<T>  {

    private Map<String,String> params;

    public enum OP {

        LIKE("$like", OperatorsImpl.like()),
        GT("$gt", OperatorsImpl.gt()),
        LT("$lt", OperatorsImpl.lt()),
        GE("$ge", OperatorsImpl.ge()),
        LE("$le", OperatorsImpl.le());

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
                    .findAny().orElseThrow(() -> new IllegalArgumentException("Operador "+name+ " não suportado"));
        }

        public OperatorStrategy getStrategy() {
            return strategy;
        }
    }

    public QuerySpecification(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!params.containsKey("query")) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        String whereQuery = params.get("query");
        Map<String, Object> filterMap = parseJson(whereQuery);

        var attrs = root.getModel().getDeclaredAttributes();

        Predicate[] predicates = filterMap.keySet().stream()
                .filter(key -> attrs.stream().anyMatch(item -> item.getName().equals(key)))
                .map(key -> processCriteria(root, criteriaBuilder, key, filterMap.get(key)))
                .toArray(value -> new Predicate[value]);

        if (predicates.length <= 0){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }else{
            return criteriaBuilder.and(predicates);
        }
    }

    private Predicate processCriteria(Root<T> root, CriteriaBuilder criteria, String originalKey, Object data) {
        var attrt = root.getModel().getAttribute(originalKey).getJavaType();

        if (Map.class.isAssignableFrom(data.getClass())){
            Map<String, Object> mapData = (Map<String, Object>) data;
            return mapData.keySet()
                    .stream()
                    .limit(1)
                    .map(key -> OP.byOperator(key).getStrategy().buildCriteria(root, criteria, originalKey, getValue(attrt,mapData.get(key)).toString()))
                    .collect(Collectors.toList()).get(0);
        }else{
            return criteria.equal(root.get(originalKey), getValue(attrt,data));
        }
    }

    private Object getValue(Class clazz, Object data){
        if (clazz.isEnum()){
            try {
                Class enumClazz = Class.forName(clazz.getName());
                Enum myEnum = Enum.valueOf(enumClazz, data.toString());
                return myEnum;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Falha ao converter dado para o enum correspondente.");
            }
        }else{
            return data;
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
