package br.ufrn.lii.genericapi;

public class OperatorsImpl {

    public static <T> OperatorStrategy like(){
        return (root,criteria,key,data) -> criteria.like(root.get(key), data.toString());
    }

    public static <T> OperatorStrategy gt(){
        return (root,criteria,key,data) -> criteria.greaterThan(root.get(key), data);
    }

    public static <T> OperatorStrategy lt(){
        return (root,criteria,key,data) -> criteria.lt(root.get(key), Double.parseDouble(data.toString()));
    }

    public static OperatorStrategy ge() {
        Object dataObj = new Object();
        return (root,criteria,key,data) -> criteria.ge(root.get(key), Double.parseDouble(data.toString()));
    }

    public static OperatorStrategy le() {
        return (root,criteria,key,data) -> criteria.le(root.get(key), Double.parseDouble(data.toString()));
    }

}
