package br.ufrn.lii.queryapi;

public class OperatorsImpl {

    public static boolean internalFilter(String key){
        return key.indexOf(".") > -1;
    }

    public static String getFieldByKey(String key) {
        var index = key.indexOf(".");
        if (index > -1){
            return key.substring(0,index);
        }
        return key;
    }

    public static String getFieldName(String key) {
        var index = key.indexOf(".");
        if (index > -1){
            return key.substring(index+1);
        }else{
            return key;
        }
    }

    public static OperatorStrategy eq(){
        return (root,criteria,key,data) -> {
            if (internalFilter(key)){
                return criteria.equal(root.join(getFieldByKey(key)).get(getFieldName(key)), data);
            }
            return criteria.equal(root.get(key), data);
        };
    }

    public static OperatorStrategy like(){
        return (root,criteria,key,data) -> {
            if (internalFilter(key)){
                return criteria.like(root.join(getFieldByKey(key)).get(getFieldName(key)), data.toString());
            }
            return criteria.like(root.get(key), data.toString());
        };
    }

    public static OperatorStrategy gt(){
        return (root,criteria,key,data) -> criteria.greaterThan(root.get(key), data);
    }

    public static OperatorStrategy lt(){
        return (root,criteria,key,data) -> criteria.lessThan(root.get(key), data);
    }

    public static OperatorStrategy ge() {
        return (root,criteria,key,data) -> criteria.greaterThanOrEqualTo(root.get(key), data);
    }

    public static OperatorStrategy le() {
        return (root,criteria,key,data) -> criteria.lessThanOrEqualTo(root.get(key), data);
    }

}
