package com.wizeline.endpoints;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wizeline.maven.learningjava.controller.BeneficiariosController;
import com.wizeline.maven.learningjava.model.BeneficiariosDTO;
import com.wizeline.maven.learningjava.service.BeneficiariosService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class EndPointsBeneTest {

	private static final Logger LOGGER = Logger.getLogger(EndPointsBeneTest.class.getName());

	@MockBean
	private BeneficiariosService servicioBeneficiarios;

	@Autowired
	private BeneficiariosController controladorBeneficiarios;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@DisplayName("Prueba Endpoint Get")
	void DadoUnServicioParaObtenerBeneficiarios_CuandoSeMandaLlamar_EntoncesSeEsperaUnResultadoExitoso()
			throws Exception {

		LOGGER.info("Se crea una lista simulando una BD de beneficiarios");
		List<BeneficiariosDTO> beneficiarios = Arrays.asList(new BeneficiariosDTO("Hermano", "24"),
				new BeneficiariosDTO("Padre", "69"));
		LOGGER.info(
				"Se crea la condicion de comportamiento, cuando se manda a llamar a beneficiarios, devuelve la lista de los beneficiarios");
		when(servicioBeneficiarios.obtieneBeneficiarios()).thenReturn(beneficiarios);

		LOGGER.info("Se hace el llamado al endpoint en este caso un get");
		MvcResult resultado = mockMvc.perform(get("/beneficiarios/obtiene")).andExpect(status().isOk()).andReturn();

		LOGGER.info("Se mapea el resultado en un arreglo de beneficiarios");
		BeneficiariosDTO[] arregloBeneficiarios = mapper.readValue(resultado.getResponse().getContentAsString(),
				BeneficiariosDTO[].class);

		LOGGER.info("El arreglo se guarda en una lista de beneficiarios para su evaluaci??n");
		List<BeneficiariosDTO> listaBeneficiarios = Arrays.asList(arregloBeneficiarios);

		LOGGER.info("Se evalua que dentro de la lista haya los Beneficiarios que se crearon al principio");
		assertTrue(listaBeneficiarios.stream().map(BeneficiariosDTO::getBeneficiarios).collect(Collectors.toList())
				.containsAll(Arrays.asList("Hermano", "Padre")));
	}

	@Test
	@DisplayName("Prueba endpoint Post")
	void DadoUnServicioParaInsertarUnNuevoBeneficiario_CuandoSeInvocaElServicio_EntoncesObtenemosOK() throws Exception {

		LOGGER.info("Se crea una lista simulando una BD de Beneficiarios");
		List<BeneficiariosDTO> Beneficiarios = Arrays.asList(new BeneficiariosDTO("Hermano", "24 "),
				new BeneficiariosDTO("Padre", "69"));

		LOGGER.info("Se crea la condicion que regresa un true cuando se inserta un beneficiario exitosamente");
		when(servicioBeneficiarios.agregaBeneficiarios(anyString(), anyString())).thenReturn(true);

		LOGGER.info(
				"Se consume el endpoint de inserci??n ya que la peticion en si espera un 200 en caso de exito, si no pues truena :D");
		MvcResult resultado = mockMvc
				.perform(post("/Beneficiarios/inserta").param("Beneficiario", "Madre").param("edad", "70"))
				.andExpect(status().isOk()).andReturn();

		LOGGER.info("Resultado: " + resultado.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("Prueba endpoint Delete")
	void DadoEndpointDelete_CuandoSeLlamaBorrar_Beneficiarios() throws Exception {

		LOGGER.info("Se simula una BD");
		List<BeneficiariosDTO> Beneficiarios = Arrays.asList(new BeneficiariosDTO("Hermano", "24"),
				new BeneficiariosDTO("Padre", "69"));

		LOGGER.info("Se crea el objeto con el beneficiario que se planea eliminar");
		BeneficiariosDTO beneficiariosBorra = new BeneficiariosDTO("Hermano", "");

		LOGGER.info(
				"Creamos un objeto con la librer??a de JACKSON el cual nos permite meter el objeto a un json y luego a una cadena"
						+ "para pasarla como par??metro");
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = writer.writeValueAsString(beneficiariosBorra);

		LOGGER.info("Se crea la condicion para que al consumir el endpoint del borrado regrese el nuevo objeto");
		when(servicioBeneficiarios.borraBeneficiarios(any(BeneficiariosDTO.class)))
				.thenReturn(any(BeneficiariosDTO.class));

		LOGGER.info("Se hace la peticion al endpoint de borrar y se le pasa como par??metro el json en cadena");
		MvcResult resultado = mockMvc
				.perform(delete("/Beneficiarios/borra").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();

		LOGGER.info("Resultado: " + resultado.getResponse().getContentAsString());

	}

	@Test
	@DisplayName("Prueba Endpoint Put")
	void DadoElServicioDeUpdate_CuandoSeMandeElBeneficiarioAModificar_EntoncesEnLaRespuestaSeDebeVerElCambio()
			throws Exception {

		LOGGER.info("Se simula una BD x4");
		List<BeneficiariosDTO> Beneficiarios = Arrays.asList(new BeneficiariosDTO("Hermano", "24"),
				new BeneficiariosDTO("Padre", "69"));

		LOGGER.info("Se crea el objeto con el beneficiario a modificar");
		BeneficiariosDTO beneficiariosModifica = new BeneficiariosDTO("Hermano", "34");

		LOGGER.info("Igual que en el anterior, se crea una cadena a partir del objete");
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = writer.writeValueAsString(beneficiariosModifica);

		LOGGER.info("Se crea la condicion para consumir el endpoint");
		when(servicioBeneficiarios.actualizaEdad(any(BeneficiariosDTO.class))).thenReturn(true);

		LOGGER.info("Se hace el llamado al endpoint de actualizar beneficiario  y se pasa el parametro en cadena");
		MvcResult resultado = mockMvc
				.perform(put("/Beneficiarios/modifica").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();

		LOGGER.info("Resultado: " + resultado.getResponse().getStatus());
	}
}
