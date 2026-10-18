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
            helper.setFrom("pruebamaxi717@gmail.com");
            helper.setSubject("Activación de tu cuenta");

            String enlaceActivacion = "http://localhost:8080/api/v1/clientes/activar?token="
                    + event.tokenActivacion();

            String html = """
                    <!DOCTYPE html>
                    <html lang="es">
                    <body style="
                        margin: 0;
                        padding: 0;
                        background-color: #f1f5f9;
                        font-family: Arial, Helvetica, sans-serif;
                        color: #334155;
                    ">
                        <table width="100%%" cellpadding="0" cellspacing="0"
                               style="background-color: #f1f5f9; padding: 35px 10px;">
                            <tr>
                                <td align="center">

                                    <table width="600" cellpadding="0" cellspacing="0"
                                           style="
                                               max-width: 600px;
                                               width: 100%%;
                                               background-color: #ffffff;
                                               border-radius: 14px;
                                               overflow: hidden;
                                           ">

                                        <!-- Encabezado -->
                                        <tr>
                                            <td align="center"
                                                style="
                                                    background-color: #0f172a;
                                                    padding: 32px 20px;
                                                    color: #ffffff;
                                                ">
                                                <div style="
                                                    font-size: 13px;
                                                    letter-spacing: 3px;
                                                    color: #38bdf8;
                                                    font-weight: bold;
                                                    margin-bottom: 12px;
                                                ">
                                                    SISTEMA BANCARIO
                                                </div>

                                                <h1 style="
                                                    margin: 0;
                                                    font-size: 28px;
                                                    color: #ffffff;
                                                ">
                                                    ¡Te damos la bienvenida!
                                                </h1>

                                                <p style="
                                                    margin: 12px 0 0;
                                                    font-size: 15px;
                                                    color: #cbd5e1;
                                                ">
                                                    Tu próximo paso comienza acá.
                                                </p>
                                            </td>
                                        </tr>

                                        <!-- Contenido -->
                                        <tr>
                                            <td style="padding: 35px 30px;">

                                                <h2 style="
                                                    margin-top: 0;
                                                    color: #0f172a;
                                                    font-size: 22px;
                                                ">
                                                    ¡Hola, %s!
                                                </h2>

                                                <p style="
                                                    font-size: 15px;
                                                    line-height: 1.8;
                                                    color: #475569;
                                                ">
                                                    Tu registro se realizó correctamente.
                                                    Estamos muy contentos de que formes
                                                    parte de nuestra plataforma.
                                                </p>

                                                <!-- Aviso -->
                                                <table width="100%%" cellpadding="0" cellspacing="0"
                                                       style="
                                                           background-color: #eff6ff;
                                                           border-left: 4px solid #2563eb;
                                                           margin: 25px 0;
                                                       ">
                                                    <tr>
                                                        <td style="
                                                            padding: 18px;
                                                            font-size: 14px;
                                                            line-height: 1.7;
                                                            color: #1e40af;
                                                        ">
                                                            <strong>Un último paso</strong><br>
                                                            Para comenzar a utilizar tu cuenta,
                                                            necesitás confirmar su activación.
                                                        </td>
                                                    </tr>
                                                </table>

                                                <p style="
                                                    text-align: center;
                                                    font-size: 15px;
                                                    color: #475569;
                                                    margin: 28px 0 22px;
                                                ">
                                                    Hacé clic en el siguiente botón:
                                                </p>

                                                <!-- Botón de activación -->
                                                <table align="center" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td align="center"
                                                            style="
                                                                background-color: #2563eb;
                                                                border-radius: 8px;
                                                            ">
                                                            <a href="%s"
                                                               style="
                                                                   display: inline-block;
                                                                   padding: 15px 30px;
                                                                   color: #ffffff;
                                                                   font-size: 15px;
                                                                   font-weight: bold;
                                                                   text-decoration: none;
                                                                   border-radius: 8px;
                                                               ">
                                                                Activar mi cuenta
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <p style="
                                                    margin-top: 28px;
                                                    font-size: 13px;
                                                    line-height: 1.7;
                                                    text-align: center;
                                                    color: #64748b;
                                                ">
                                                    Por seguridad, este enlace vence
                                                    después de 24 horas.
                                                </p>

                                                <hr style="
                                                    border: none;
                                                    border-top: 1px solid #e2e8f0;
                                                    margin: 30px 0;
                                                ">

                                                <p style="
                                                    font-size: 13px;
                                                    line-height: 1.7;
                                                    color: #64748b;
                                                ">
                                                    Si el botón no funciona, podés copiar
                                                    y pegar este enlace en tu navegador:
                                                </p>

                                                <p style="
                                                    font-size: 12px;
                                                    line-height: 1.7;
                                                    word-break: break-all;
                                                ">
                                                    <a href="%s"
                                                       style="color: #2563eb;">
                                                        %s
                                                    </a>
                                                </p>

                                            </td>
                                        </tr>

                                        <!-- Pie -->
                                        <tr>
                                            <td align="center"
                                                style="
                                                    background-color: #f8fafc;
                                                    padding: 24px 20px;
                                                    border-top: 1px solid #e2e8f0;
                                                ">
                                                <p style="
                                                    margin: 0 0 8px;
                                                    color: #334155;
                                                    font-size: 14px;
                                                    font-weight: bold;
                                                ">
                                                    Sistema Bancario
                                                </p>

                                                <p style="
                                                    margin: 0;
                                                    color: #94a3b8;
                                                    font-size: 12px;
                                                    line-height: 1.6;
                                                ">
                                                    Este correo fue generado automáticamente.
                                                    Por favor, no respondas a este mensaje.
                                                </p>
                                            </td>
                                        </tr>

                                    </table>

                                </td>
                            </tr>
                        </table>
                    </body>
                    </html>
                    """.formatted(
                    event.nombre(),
                    enlaceActivacion,
                    enlaceActivacion,
                    enlaceActivacion);

            helper.setText(html, true);

            mailSender.send(mensaje);

        } catch (MessagingException e) {
            throw new RuntimeException(
                    "Error al enviar el email de activación", e);
        }
    }
}
