package com.financialportfolio.backend.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class CastUtil {

    private static Log logger;

    private CastUtil() {
        super();
        logger = LogFactory.getLog(getClass());
    }

    /**
     * Realiza a conversão de uma coleção genérica para uma lista da Classe do tipo
     * T passada por parâmetro.
     * 
     * @param <T>           - tipo genérico que representa a classe.
     * @param clazz         - classe do tipo T a ser utilizada na lista genérica.
     * @param rawCollection - coleção genérica a ser convertida.
     * @return lista do tipo T.
     */
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {

        List<T> result = new ArrayList<>(rawCollection.size());

        for (Object o : rawCollection) {
            try {
                result.add(clazz.cast(o));
            } catch (ClassCastException e) {
                logger.info(e.getLocalizedMessage());
            }
        }

        return result;
    }

    /**
     * Realiza a conversão do texto de camelCase para Snake_Case.
     * 
     * @param str - texto
     * @return texto em modo snake_case.
     */
    public static String castCamelToSnake(String str) {

        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return str.replaceAll(regex, replacement).toLowerCase();
    }

}
