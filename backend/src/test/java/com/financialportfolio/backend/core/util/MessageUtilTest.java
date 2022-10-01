package com.financialportfolio.backend.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@ExtendWith(MockitoExtension.class)
class MessageUtilTest {

    @InjectMocks
    private MessageUtil messageUtil;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testarRecuperarMensagem() {
        messageUtil.setLocale(new Locale("pt", "BR"));

        when(messageSource.getMessage("mensagem", null, new Locale("pt", "BR")))
                .thenReturn("Mensagem proveniente do MessageSource");

        assertEquals("Mensagem proveniente do MessageSource", messageUtil.getMessageBy("mensagem"));
        assertEquals("BR", messageUtil.getLocale().getCountry());
        assertEquals("pt", messageUtil.getLocale().getLanguage());
    }

    @Test
    public void testarFalhaAoRecuperarMensagem() {
        messageUtil.setLocale(new Locale("pt", "BR"));

        when(messageSource.getMessage("mensagem", null, new Locale("pt", "BR")))
                .thenThrow(NoSuchMessageException.class);
        when(messageSource.getMessage(null, null, new Locale("pt", "BR"))).thenThrow(NoSuchMessageException.class);
        when(messageSource.getMessage("", null, new Locale("pt", "BR"))).thenThrow(NoSuchMessageException.class);

        assertThrows(NoSuchMessageException.class, () -> messageUtil.getMessageBy("mensagem"));
        assertThrows(NoSuchMessageException.class, () -> messageUtil.getMessageBy(null));
        assertThrows(NoSuchMessageException.class, () -> messageUtil.getMessageBy(""));
    }

    @Test
    public void testarRecuperarMensagemComArgumentos() {
        messageUtil.setLocale(new Locale("pt", "BR"));
        Object[] args = { 1 };

        when(messageSource.getMessage("mensagem", args, new Locale("pt", "BR")))
                .thenReturn("Mensagem proveniente do MessageSource: " + args[0]);

        assertEquals("Mensagem proveniente do MessageSource: 1", messageUtil.getMessageBy("mensagem", args));
    }

    @Test
    public void testarFalhaAoRecuperarMensagemComArgumentos() {
        messageUtil.setLocale(new Locale("pt", "BR"));
        Object[] args = { 1 };

        when(messageSource.getMessage("mensagem", args, new Locale("pt", "BR")))
                .thenThrow(NoSuchMessageException.class);
        when(messageSource.getMessage(null, args, new Locale("pt", "BR"))).thenThrow(NoSuchMessageException.class);
        when(messageSource.getMessage("", args, new Locale("pt", "BR"))).thenThrow(NoSuchMessageException.class);

        assertThrows(NoSuchMessageException.class, () -> messageUtil.getMessageBy("mensagem", args));
        assertThrows(NoSuchMessageException.class, () -> messageUtil.getMessageBy(null, args));
        assertThrows(NoSuchMessageException.class, () -> messageUtil.getMessageBy("", args));
    }

}
