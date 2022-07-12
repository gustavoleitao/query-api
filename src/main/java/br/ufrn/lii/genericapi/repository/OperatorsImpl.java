package br.ufrn.lii.genericapi.repository;


public class OperatorsImpl {

    public static <T> OperatorStrategy like(){
        return (root,criteria,key,data) -> criteria.like(root.get(key), data);
    }

    public static <T> OperatorStrategy gt(){
        return (root,criteria,key,data) -> criteria.gt(root.get(key), Double.parseDouble(data));
    }

    public static <T> OperatorStrategy lt(){
        return (root,criteria,key,data) -> criteria.lt(root.get(key), Double.parseDouble(data));
    }

}
