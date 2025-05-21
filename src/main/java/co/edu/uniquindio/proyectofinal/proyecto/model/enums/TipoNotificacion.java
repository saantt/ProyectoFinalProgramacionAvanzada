package co.edu.uniquindio.proyectofinal.proyecto.model.enums;

public enum TipoNotificacion {
    NUEVO_REPORTE("nuevo_reporte"),
    CAMBIO_ESTADO("cambio_estado"),
    COMENTARIO("comentario"),
    ALERTA("alerta"),
    IMPORTANTE("importante");

    private final String valor;

    TipoNotificacion(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoNotificacion fromString(String text) {
        for (TipoNotificacion tipo : TipoNotificacion.values()) {
            if (tipo.valor.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de notificación no válido: " + text);
    }
}