package cl.ucn.disc.isof.fivet.domain.model;

import lombok.Getter;
import lombok.Setter;
import com.durrutia.ebean.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by pablofb on 09-11-16.
 */
public class Control extends BaseModel {

    /**
     * Fecha
     */
    @Getter
    @Setter
    private Date fecha;

    /**
     * Proximo control
     */
    @Getter
    @Setter
    private Date ProximoControl;

    /**
     * Temperatura
     */
    @Getter
    @Setter
    private Double temperatura;

    /**
     * Peso
     */
    @Getter
    @Setter
    private Double peso;

    /**
     * Altura
     */
    @Getter
    @Setter
    private Double altura;

    /**
     * Diagnostico
     */
    @Getter
    @Setter
    private String diagnostico;

    /**
     * Nota
     */
    @Getter
    @Setter
    private Integer nota;

    /**
     * Examenes
     */
    @Getter
    @Setter
    private List<Examen> examenes[];

}
