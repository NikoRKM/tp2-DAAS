package ar.edu.unju.fi.tp2.events;

import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteRegistradoListener {

    private final JavaMailSender mailSender;

    @Async
    @EventListener
    public void enviarEmailActivacion(ClienteRegistradoEvent event) {

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(event.email());
            helper.setSubject("Activación de tu cuenta");

            String enlaceActivacion = "http://localhost:8081/api/v1/clientes/activar?token="
                    + event.tokenActivacion();

            String html = """
                    <!DOCTYPE html>
                    <html>
                    <body>
                        <h1>¡Bienvenido, %s!</h1>

                        <p>Tu cuenta fue creada correctamente.</p>

                        <p>Para activar tu cuenta, hacé clic en el siguiente botón:</p>

                        <p>
                            <a href="%s"
                               style="
                               background-color:#007bff;
                               color:white;
                               padding:12px 20px;
                               text-decoration:none;
                               border-radius:5px;">
                                Activar mi cuenta
                            </a>
                        </p>

                        <p>Este enlace tiene una validez de 24 horas.</p>

                        <p>Saludos.</p>
                    </body>
                    </html>
                    """.formatted(
                    event.nombre(),
                    enlaceActivacion);

            helper.setText(html, true);

            mailSender.send(mensaje);

        } catch (MessagingException e) {
            throw new RuntimeException(
                    "Error al enviar el email de activación", e);
        }
    }
}
