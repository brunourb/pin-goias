package gov.goias.pin;

import gov.goias.pin.entity.Colaborador;
import gov.goias.pin.entity.UnidadeAdministrativa;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColaboradorResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private Colaborador colaborador;
    private String defaultURI;
    private String testURI;

    /**
     * Cria o caminho feliz dos testes
     */
    @Before
    public void setup(){
        UnidadeAdministrativa unidade = UnidadeAdministrativa.builder().id(1L).nome("Unidade-1").build();
        defaultURI = restTemplate.getRootUri() + "/pin/api/colaboradores/";
        testURI     = defaultURI + "1";
        headers     = new HttpHeaders();
        colaborador = Colaborador.builder()
                                 .id(8L)
                                 .nome("Colaborador-8")
                                 .cpf("56496885591")
                                 .email("colaborador8@test.com")
                                 .dataNascimento(LocalDate.now().minusDays(1))
                                 .sexo(Colaborador.Sexo.FEMININO)
                                 .unidade(unidade).build();
    }

    /**
     * Lista todos os registros na tabela
     */
    @Test
    public void testListar() {
        HttpEntity<Colaborador> entity  = new HttpEntity(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(defaultURI,HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    /**
     * Cria um novo colaborador
     */
    @Test
    public void testCriar() {
        HttpEntity<Colaborador> entity  = new HttpEntity(colaborador, headers);
        ResponseEntity<String> response = restTemplate.exchange(defaultURI,HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().get(HttpHeaders.LOCATION)).isNotEmpty();
        assertThat(response.getHeaders().get(HttpHeaders.LOCATION).get(0)).isEqualTo("/api/colaboradores/8");
    }

    /**
     * Cria um novo colaborador
     */
    @Test
    public void testCriarInvalido() {
        colaborador.setCpf(null);
        HttpEntity<Colaborador> entity  = new HttpEntity(colaborador, headers);
        ResponseEntity<String> response = restTemplate.exchange(defaultURI,HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Altera um colaborador existente
     */
    @Test
    public void testAlterar() {
        colaborador.setId   (1L);
        colaborador.setNome ("Colaborador-1 Alterado");
        HttpEntity<Colaborador> entity  = new HttpEntity(colaborador, headers);
        ResponseEntity<String> response = restTemplate.exchange(testURI,HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        entity = new HttpEntity(null, headers);
        response = restTemplate.exchange(testURI,HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Colaborador-1 Alterado");
    }

    /**
     * Remove um colaborador existente
     */
    @Test
    public void testDeletar() {
        HttpEntity<Colaborador> entity  = new HttpEntity(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(testURI,HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Busca um colaboador pelo seu identificador
     */
    @Test
    public void testBuscarPorId() {
        HttpEntity<Colaborador> entity  = new HttpEntity(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(testURI,HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void testBuscarPorIdNaoEncontrado() {
        HttpEntity<Colaborador> entity  = new HttpEntity(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(defaultURI + "10",HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}