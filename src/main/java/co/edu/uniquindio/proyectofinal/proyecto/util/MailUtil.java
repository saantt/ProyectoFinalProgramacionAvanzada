package co.edu.uniquindio.proyectofinal.proyecto.util;
public class MailUtil {

    public static String buildVerificationEmail(String username, String code) {
        return "Hola " + username + ",\n\n"
                + "Tu código de verificación es: " + code + "\n\n"
                + "Gracias por usar nuestra plataforma de seguridad ciudadana.";
    }

    public static String buildIncidentReportNotification(String incidentDescription) {
        return "Se ha reportado un nuevo incidente:\n\n" + incidentDescription;
    }
}