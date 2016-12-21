package cl.ucn.disc.isof.fivet.domain.model;

import lombok.Getter;
import lombok.Setter;
import com.durrutia.ebean.BaseModel;

import javax.persistence.Column;
import javax.persistence.OneToMany;
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
    @Column
    private Date fecha;

    /**
     * Proximo control
     */
    @Getter
    @Setter
    @Column
    private Date ProximoControl;

    /**
     * Temperatura
     */
    @Getter
    @Setter
    @Column
    private Double temperatura;

    /**
     * Peso
     */
    @Getter
    @Setter
    @Column
    private Double peso;

    /**
     * Altura
     */
    @Getter
    @Setter
    @Column
    private Double altura;

    /**
     * Diagnostico
     */
    @Getter
    @Setter
    @Column
    private String diagnostico;

    /**
     * Nota
     */
    @Getter
    @Setter
    @Column
    private Integer nota;

    /**
     * Examenes
     */
    @Getter
    @Setter
    @Column
    @OneToMany
    private List<Examen> examenes[];

    /**
     * Veterinario
     */
    @Getter
    @Setter
    @Column
    private Persona veterinario;

}
