package br.ufrn.lii.genericapi;

import java.util.Date;

public class ConverterUtil {

    public static Comparable toComparable(Class clazz, Object data){
        if (clazz.isEnum()) {
            try {
                Class enumClazz = Class.forName(clazz.getName());
                Enum myEnum = Enum.valueOf(enumClazz, data.toString());
                return myEnum;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Falha ao converter dado para o enum correspondente.");
            }
        }else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)){
            return Boolean.valueOf(data.toString());
        }else if (Date.class.isAssignableFrom(clazz)){
            return DateUtil.parseDate(data.toString());
        }else{
            return data.toString();
        }
    }

}
