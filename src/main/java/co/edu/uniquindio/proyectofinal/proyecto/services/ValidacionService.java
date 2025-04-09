package co.edu.uniquindio.proyectofinal.proyecto.services;

public interface ValidacionService {

    /**
     * Valida si el código enviado por el usuario coincide con el almacenado.
     *
     * @param codigo código enviado por el usuario
     * @param idUsuario id del usuario que intenta validar
     * @return true si es válido, false si no lo es
     */
    boolean validarCodigo(String codigo, String idUsuario);
}
