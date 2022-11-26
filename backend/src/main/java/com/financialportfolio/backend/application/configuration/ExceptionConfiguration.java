package com.financialportfolio.backend.application.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class ExceptionConfiguration {

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @PostConstruct
    private void configureDispatcherServlet() {

        /**
         * Esta configuração é necessária para direcionar o erro 404 (NOT FOUND) para
         * ser tratado pelo método handleNoHandlerFoundException da classe
         * {@link ErrorController}, caso contrário, a resposta enviada ao solicitante
         * seria a resposta padrão do Spring Framework.
         */
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
