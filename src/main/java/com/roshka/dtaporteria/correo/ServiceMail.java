package com.roshka.dtaporteria.correo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
public class ServiceMail {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration config;

    private String from = "roshkagram@roshka.com";

    public void sendEmailWithHTML(String to, String subject, Map<String, Object> model) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
            helper.addAttachment("logo_insignia.png", new ClassPathResource("static/img/logo_insignia.png"));

            Template t = config.getTemplate("recuperarContra.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(to);
            helper.setText(html, true);
            helper.setSubject(subject);
            helper.setFrom(from);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
