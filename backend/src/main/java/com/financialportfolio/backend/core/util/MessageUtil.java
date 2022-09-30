package com.financialportfolio.backend.core.util;

import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public final class MessageUtil {

    @Autowired
    private MessageSource messageSource;

    private Locale locale = new Locale("pt", "BR");

    private MessageUtil() {
        super();
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = Objects.requireNonNull(locale);
    }

    /**
     * Busca a mensagem no MessageResource fornecido por meio do resource code
     * passado por parâmetro
     * 
     * @param resourceCode - identificador do recurso da mensagem
     * @return retorna a mensagem ou lança a exceção NoSuchMessageException
     * @throws NoSuchMessageException
     */
    public String getMessageBy(String resourceCode) {
        return messageSource.getMessage(resourceCode, null, locale);
    }

    /**
     * Busca a mensagem no MessageResource fornecido por meio do resource code
     * passado por parâmetro e adiciona argumentos nos parâmetros configurados na
     * mensagem
     * 
     * @param resourceCode - identificador do recurso da mensagem
     * @param args         - um array de argumentos que serão utilizados no
     *                     preenchimento dos parâmetros da mensagem (parâmetros se
     *                     parecem com "{0}", "{1}", "{2}"...)
     * @return retorna a mensagem ou lança a exceção NoSuchMessageException
     * @throws NoSuchMessageException
     */
    public String getMessageBy(String resourceCode, Object... args) {
        return messageSource.getMessage(resourceCode, args, locale);
    }

}
