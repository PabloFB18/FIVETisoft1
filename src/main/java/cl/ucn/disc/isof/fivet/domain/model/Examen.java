package cl.ucn.disc.isof.fivet.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by pablofb on 09-11-16.
 */
public class Examen {

    /**
     * Nombre
     */
    @Getter
    @Setter
    private String nombre;

    /**
     * Fecha
     */
    @Getter
    @Setter
    private Date fecha;

    /**
     * Resultado
     */
    @Getter
    @Setter
    private String resultado;
}
