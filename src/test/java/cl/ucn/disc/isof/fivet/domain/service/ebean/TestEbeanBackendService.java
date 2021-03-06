package cl.ucn.disc.isof.fivet.domain.service.ebean;

import cl.ucn.disc.isof.fivet.domain.model.Control;
import cl.ucn.disc.isof.fivet.domain.model.Paciente;
import cl.ucn.disc.isof.fivet.domain.model.Persona;
import cl.ucn.disc.isof.fivet.domain.service.BackendService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.ToDateParser;
import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import java.util.*;

/**
 * Clase de testing del {@link BackendService}.
 */
@Slf4j
@FixMethodOrder(MethodSorters.DEFAULT)
public class TestEbeanBackendService {

    /**
     * Todos los test deben terminar antes de 60 segundos.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(60);

    /**
     * Configuracion de la base de datos:  h2, hsql, sqlite
     * WARN: hsql no soporta ENCRYPT
     */
    private static final String DB = "h2";

    /**
     * Backend
     */
    private BackendService backendService;

    /**
     * Cronometro
     */
    private Stopwatch stopWatch;

    /**
     * Antes de cada test
     */
    @Before
    public void beforeTest() {

        stopWatch = Stopwatch.createStarted();
        log.debug("Initializing Test Suite with database: {}", DB);

        backendService = new EbeanBackendService(DB);
        backendService.initialize();
    }

    /**
     * Despues del test
     */
    @After
    public void afterTest() {

        log.debug("Test Suite done. Shutting down the database ..");
        backendService.shutdown();

        log.debug("Test finished in {}", stopWatch.toString());
    }

    /**
     * Test de la persona
     */
    @Test
    public void testPersona() {

        final String rut = "1-1";
        final String nombre = "Este es mi nombre";
        final String email = "mi.email@gmail.com";

        // Insert into backend
        {
            final Persona persona = Persona.builder()
                    .nombre(nombre)
                    .rut(rut)
                    .password("durrutia123")
                    .tipo(Persona.Tipo.CLIENTE)
                    .email(email)
                    .build();

            persona.insert();

            log.debug("Persona to insert: {}", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
        }

        // Get persona por rut from backend
        {
            final Persona persona = backendService.getPersona(rut);
            log.debug("Persona founded: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
            Assert.assertEquals("Ruts distintos!", rut, persona.getRut());
            Assert.assertNotNull("Pacientes null", persona.getPacientes());
            Assert.assertTrue("Pacientes != 0", persona.getPacientes().size() == 0);

            // Update nombre
            persona.setNombre(nombre);
            persona.update();
        }

        // Get persona por rut nuevo nombre from backend
        {
            final Persona persona = backendService.getPersona(rut);
            log.debug("Persona founded: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertEquals("Nombres distintos!", nombre, persona.getNombre());
        }

        // Get persona por rut from backend ingresando otro rut
        {
            final Persona persona = backendService.getPersona("2-2");
            Assert.assertNull("found Persona", persona);
        }

        // Get persona por email from backend
        {
            final Persona persona = backendService.getPersona(email);
            log.debug("Persona founded: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
            Assert.assertEquals("Emails distintos!", email, persona.getEmail());
            Assert.assertNotNull("Pacientes null", persona.getPacientes());
            Assert.assertTrue("Pacientes != 0", persona.getPacientes().size() == 0);
        }

        // Get persona por email from backend ingresando otro email
        {
            final Persona persona = backendService.getPersona("otro.email@gmail.com");
            Assert.assertNull("Can't find Persona", persona);
        }

    }

    /**
     * Test del paciente
     */
    @Test
    public void testPaciente() {

        final Integer numero = 11;
        final String nombre = "Spike";

        //test get pacientes vacio
        {
            final List<Paciente> pacientes = backendService.getPacientes();
            Assert.assertNotNull("Pacientes null", pacientes);
            Assert.assertTrue("Pacientes != 0", pacientes.size() == 0);
        }

        //test get pacientespor nombre vacio
        {
            final List<Paciente> pacientes = backendService.getPacientesPorNombre(nombre);
            Assert.assertNotNull("Pacientes null", pacientes);
            Assert.assertTrue("Pacientes != 0", pacientes.size() == 0);
        }

        // Insert into backend
        {
            final Paciente paciente = Paciente.builder()
                    .numero(numero)
                    .nombre(nombre)
                    .especie("Pastor Aleman")
                    .sexo(Paciente.Sexo.MACHO)
                    .color("verde")
                    .build();

            paciente.insert();

            log.debug("Paciente to insert: {}", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());
        }

        //test get pacientes
        {
            final List<Paciente> pacientes = backendService.getPacientes();
            Assert.assertNotNull("Pacientes null", pacientes);
            Assert.assertNotNull("No hay pacientes", pacientes.get(0));
            Assert.assertTrue("Pacientes != 1", pacientes.size() == 1);
            Assert.assertEquals("Numeros distintos", numero, pacientes.get(0).getNumero());
        }

        //test get paciente por numeroPaciente
        {
            final Paciente paciente = backendService.getPaciente(numero);
            log.debug("Paciente founded: {}", paciente);
            Assert.assertNotNull("Paciente null", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());
            Assert.assertEquals("Numeros distintos", numero, paciente.getNumero());
        }

        //test get paciente por numeroPaciente con numero falso
        {
            final Paciente paciente = backendService.getPaciente(22);
            Assert.assertNull("Se encontro un paciente", paciente);
        }

        //test get pacientes por nombre
        {
            final List<Paciente> pacientes = backendService.getPacientesPorNombre(nombre);
            Assert.assertNotNull("Pacientes null", pacientes);
            Assert.assertNotNull("No hay pacientes", pacientes.get(0));
            Assert.assertTrue("Pacientes != 1", pacientes.size() == 1);
            Assert.assertEquals("Nombres distintos", nombre, pacientes.get(0).getNombre());
        }

        //test get pacientes por nombre con nombre falso
        {
            final List<Paciente> pacientes = backendService.getPacientesPorNombre("otro");
            Assert.assertNotNull("Pacientes null", pacientes);
            Assert.assertTrue("Pacientes != 0", pacientes.size() == 0);
        }

    }

    /**
     * Test del control
     */
    @Test
    public void testControl() {

        final String rutVeterinario = "1-1";
        final Integer numeroPaciente = 112;
        final Date fecha = new Date();

        //test get controles veterinario por rut veterinario vacio
        {
            final List<Control> controles = backendService.getControlesVeterinario(rutVeterinario);
            Assert.assertNotNull("Pacientes null", controles);
            Assert.assertTrue("Pacientes != 0", controles.size() == 0);
        }

        //test agregar control
        {
            final Paciente paciente = Paciente.builder()
                    .numero(numeroPaciente)
                    .nombre("Spike")
                    .especie("Pastor Aleman")
                    .sexo(Paciente.Sexo.MACHO)
                    .color("verde")
                    .controles(new ArrayList<>())
                    .build();

            paciente.insert();

            final Control control = Control.builder()
                    .fecha(fecha)
                    .temperatura(1.1)
                    .peso(23.0)
                    .altura(50.0)
                    .rutVeterinario(rutVeterinario)
                    .build();

            backendService.agregarControl(control, numeroPaciente);

            //revisar que el control sea el mismo y que este en el mismo paciente
            Paciente pacienteBD = backendService.getPaciente(numeroPaciente);
            log.debug("Control founded: {}", pacienteBD.getControles().get(0));
            Assert.assertNotNull("no se encontro el control", pacienteBD.getControles().get(0));
            Assert.assertNotNull("Objeto sin id", pacienteBD.getControles().get(0).getId());
            Assert.assertEquals("fecha control diferente", fecha, pacienteBD.getControles().get(0).getFecha());
        }

        //test get controles veterinario por rut veterinario
        {
            final List<Control> controles = backendService.getControlesVeterinario(rutVeterinario);
            Assert.assertNotNull("no se encontro controles", controles);
            Assert.assertNotNull("No hay contrles", controles.get(0));
            Assert.assertTrue("Controles != 1", controles.size() == 1);
            Assert.assertEquals("fecha control diferente", fecha, controles.get(0).getFecha());
        }

    }

}
