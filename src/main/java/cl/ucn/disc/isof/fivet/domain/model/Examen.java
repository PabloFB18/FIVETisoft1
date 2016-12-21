package cl.ucn.disc.isof.fivet.domain.model;

import com.durrutia.ebean.BaseModel;
import lombok.*;

import javax.persistence.Column;
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
public class Examen extends BaseModel {

    /**
     * Nombre
     */
    @Getter
    @Setter
    @Column
    private String nombre;

    /**
     * Fecha
     */
    @Getter
    @Setter
    @Column
    private Date fecha;

    /**
     * Resultado
     */
    @Getter
    @Setter
    @Column
    private String resultado;
}
