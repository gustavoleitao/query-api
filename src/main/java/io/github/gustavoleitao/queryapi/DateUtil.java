package io.github.gustavoleitao.queryapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe utilitária de operações em datas.
 */
public class DateUtil {

    private DateUtil(){

    }

    private static List<String> validPatterns = Collections.synchronizedList(
            Arrays.asList("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "yyyy-MM-dd HH:mm:ss.SSS'Z'",
                    "yyyy-MM-dd HH:mm:ss.SSS",
                    "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy-MM-dd HH:mm:ss'Z'",
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd"));

    public static Date parseDate(String date) {
        for (String pattern : validPatterns){
            var optional = tryWith(pattern, date);
            if (optional.isPresent()){
                return optional.get();
            }
        }
        throw new IllegalArgumentException("Data "+ date +" em formato inválido.");
    }

    private static Optional<Date> tryWith(String pattern, String date){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return Optional.of(sdf.parse(date));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

}

