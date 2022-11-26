package com.financialportfolio.backend.domain.service.implementation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.financialportfolio.backend.core.exception.InvalidUrlException;
import com.financialportfolio.backend.core.util.ConstantsUtil;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.service.EmailService;

import io.vavr.control.Either;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Either<Exception, Integer> sendPasswordRecoveryTokenEmail(
            final HttpServletRequest request,
            final String token, 
            final User user) {

        try {
            String passwordRecoveryUri = buildPasswordRecoveryUrl(request, user.getUsername(), token);
            mailSender.send(buildPasswordRecoveryTokenEmail(passwordRecoveryUri, token, user));
        } catch (Exception e) {
            return Either.left(e);
        }

        return Either.right(1);
    }

    /**
     * Monta a URL de recuperação de senha, adicionando as informações de email e
     * token de autenticação a mesma.
     * 
     * @param request  - HTTP request.
     * @param username - email do usuário.
     * @param token    - Token de autenticação.
     * @return URL de recuperação de senha.
     * @throws InvalidUrlException - Exceção que indica má formação na URL.
     */
    private String buildPasswordRecoveryUrl(
            final HttpServletRequest request, 
            final String username, 
            final String token) throws InvalidUrlException {

        String[] schemes = { request.getScheme() };

        final String appUrl = (
                schemes[0] + "://" + 
                request.getServerName() + ":" + 
                request.getServerPort() + 
                request.getHeader(ConstantsUtil.CHANGE_PASSWORD_URI)).trim();

        if (!(new UrlValidator(schemes)).isValid(appUrl)) {
            throw new InvalidUrlException("URL de recuperação de e-mail inválida: " + appUrl);
        }

        return String.format(
                appUrl + "?" + ConstantsUtil.EMAIL + "=%s&" + ConstantsUtil.TOKEN + "=%s", 
                username, 
                token);
    }

    /**
     * Monta o email de recuperação de senha.
     * 
     * @param url   - URL de recuperação de senha.
     * @param token - Token de autenticação.
     * @param user  - Usuário.
     * @return Email de recuperação de senha
     * @throws MessagingException - Exceção que indica má formação na montagem do
     *                            email.
     */
    private MimeMessage buildPasswordRecoveryTokenEmail(
            final String url, 
            final String token, 
            final User user) throws MessagingException {

        final String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(Calendar.getInstance()
                .getTime())
                .toString();

        final String bodyMessage = new StringBuilder()
                .append("Olá, <b>" + String.format("%s %s", user.getFirstName(), user.getLastName()) + "</b>.<br/><br/>")
                .append("Recebemos uma solicitação para restaurar sua senha de acesso em nosso site.<br/>")
                .append("Ela ocorreu em: <b>" + dataHora + "</b>.<br/><br/>")
                .append("Se você reconhece essa ação, clique no link abaixo para prosseguir:<br/><br/>")
                .append("<a href='" + url + "'>REDEFINIR SENHA</a><br/><br/>")
                .append("Atenciosamente,<br/>")
                .append("<b>Equipe Portfólio Financeiro</b>")
                .toString();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setSubject("[Portfólio Financeiro] Recuperação de Senha");
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(user.getUsername());
        helper.setText(bodyMessage, true);

        return mimeMessage;
    }

}
