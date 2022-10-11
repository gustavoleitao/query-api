package io.github.gustavoleitao.queryapi;

import java.util.Date;
import java.util.UUID;

/**
 * Classe utilitária de conversão de dados
 */
public class ConverterUtil {

    public static Comparable toComparable(Class clazz, String key, Object data) {

        if (clazz.isEnum()) {
            return createEnum(clazz, data);
        } else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
            return Boolean.valueOf(data.toString());
        } else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
            return Integer.valueOf(data.toString());
        } else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz)) {
            return Double.valueOf(data.toString());
        } else if (Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz)) {
            return Float.valueOf(data.toString());
        } else if (Short.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz)) {
            return Short.valueOf(data.toString());
        } else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
            return Long.valueOf(data.toString());
        } else if (Date.class.isAssignableFrom(clazz)) {
            return DateUtil.parseDate(data.toString());
        } else if (String.class.isAssignableFrom(clazz)) {
            return data.toString();
        } else if (UUID.class.isAssignableFrom(clazz)) {
            return UUID.fromString(data.toString());
        } else {
            return data.toString();
        }

    }

    private static Enum createEnum(Class clazz, Object data) {
        try {
            Class enumClazz = Class.forName(clazz.getName());
            Enum myEnum = Enum.valueOf(enumClazz, data.toString());
            return myEnum;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Falha ao converter dado para o enum correspondente.");
        }
    }

}
