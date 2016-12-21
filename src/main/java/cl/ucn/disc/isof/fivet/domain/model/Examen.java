package cl.ucn.disc.isof.fivet.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by pablofb on 09-11-16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
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
