package com.wizeline.mongo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import com.wizeline.maven.learningjava.model.BeneficiariosDTO;
import com.wizeline.maven.learningjava.repository.RepositorioBeneficiarios;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class CRUDTest {

    private static final Logger LOGGER = Logger.getLogger(CRUDTest.class.getName());

    @Autowired
    private RepositorioBeneficiarios repositorioBeneficiarios;

    @BeforeAll
    public void Organizar() {
        LOGGER.info("Se Pobla la Base de Datos en un Before All");
        repositorioBeneficiarios.save(new BeneficiariosDTO("Hermano","24"));
        repositorioBeneficiarios.save(new BeneficiariosDTO("Padre6 ","69"));
    }


    @Test
    @DisplayName("Prueba Read")
    public void DadoUnaBDDeBeneficiarios_CuandoSeMandeLlamar_EntoncesDevuelveLosBeneficiarios() {
        LOGGER.info("Se guarda en una lista los Beneficiarios de la base de datos");
        List<BeneficiariosDTO> listaBeneficiarios = repositorioBeneficiarios.findAll();

        LOGGER.info("Se verifica que el tamaño de la lista sea del numero de Beneficiarios que ya sabemos que hay");
        LOGGER.info("Se mapea y se verifica que estén los 2 Beneficiarios que agregamos");
        assertAll(
                () -> assertEquals(2,listaBeneficiarios.size()),
                () -> assertTrue(listaBeneficiarios.stream().map(BeneficiariosDTO::getBeneficiarios).collect(Collectors.toList()).containsAll(List.of("Hermano","Padre")))
        );
    }

    @Test
    @DisplayName("Prueba Create")
    public void DadoUnaBDDeBeneficiarios_CuandoSeInserteUnBeneficiario_EntoncesAlConsultarDeNuevoSeVeraElBeneficiarioAgregado()
    {
        LOGGER.info("Se guarda un nuevo beneficiario");
        BeneficiariosDTO beneficiarios = new BeneficiariosDTO("Antigua y Barbuda","200");
        BeneficiariosDTO beneficiariosGuardado = repositorioBeneficiarios.save(beneficiarios);
        LOGGER.info("Se verifica que el pais se haya agregado");
        assertEquals("Antigua y Barbuda", beneficiariosGuardado.getBeneficiarios());
    }

    @Test
    @DisplayName("Prueba Delete")
    public void DadaUnaBD_CuandoSeElimineUnBeneficiario_EntoncesDevuelveLosBeneficiariosRestantes() {
        LOGGER.info("Se hace el borrado de un beneficiario por ID");
        repositorioBeneficiarios.deleteById("Hermano");
        List<BeneficiariosDTO> beneficiarios = repositorioBeneficiarios.findAll();
        LOGGER.info("Se verifica que el tamaño de la lista corresponda al nuevo numero de Beneficiarios");
        assertEquals(1,beneficiarios.size());
    }

    @Test
    @DisplayName("Prueba Update")
    public void DadaUnaBD_CuandoSeActualiceUnBeneficiario_EntoncesDevuelveLosBeneficiariosConLasModificacionesCorrespondientes() {
        LOGGER.info("Se Prepara todo para actualizar un beneficiario, mediante un guardado");
        BeneficiariosDTO beneficiarios = repositorioBeneficiarios.findById("Hermano").get();
        beneficiarios.setEdad("25");
        LOGGER.info("Se gurda el beneficiario con la nueva edad");
        repositorioBeneficiarios.save(beneficiarios);

        beneficiarios = repositorioBeneficiarios.findById("Hermano").get();

        LOGGER.info("Se verifica que la nueva poblacion del beneficiarios, sea la que nosotros definimos");
        assertEquals("25",beneficiarios.getEdad());
    }

}
