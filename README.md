# Padrão de interoperabilidade de serviços - Goiás: Implementação de Referência utilizando a tecnologia Java

## Documentação
	
	
[Padrão de Interoperabilidade](https://github.com/goias/pin-goias/blob/master/pin-doc/Padrao_de_Interoperabilidade.doc?raw=true)
		

## Samples

	
### Java		

#### Principais Tecnologias

Java 8, Spring Web MVC, Swagger 2.0 (OpenAPI)

#### OpenAPI

A especificação OpenAPI foi criada por um consórcio, a Open API Iniciative (OAI), formado por especialistas que reconhecem a importância da padronização de como as APIs REST são descritas. Várias grandes empresas são membros deste consórcio, entre elas, a IBM, Google, Microsoft, SmartBear (que doou a especificação do Swagger 2.0 como base para a primeira versão da especificação OpenAPI), PayPal, entre outros.

A especificação utilizada está disponível em: https://swagger.io/docs/specification/2-0/adding-examples/

#### Swagger 2.0

A implementação da especificação utilizada foi o framework Swagger 2.0, que fornece um conjunto de bibliotecas e anotações para a geração da documentação da API durante a implementação do serviço.

Observação: Para API que serão consumidas pela ferramenta WSO2 - API Manager, utilizar somente a especificação Swagger 2. Não utilizar a especificação Open Api 3.0, pois o WSO2 - API Manager, ainda não lê swagger nesta versão/especifica 3.0

#### Maven dependências

	<dependencies>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>5.0.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>
	</dependencies>

##### @API

```java
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/aluno")
    @Api(value = "Operações de CRUD em Aluno")
    public class AlunoCmds {
```

##### @ApiOperation

```java
    /**
     * Armazena um Aluno no sistema.
     *
     * @param aluno Informações do aluno
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(code = 201, message = "Criado.")
    @ApiOperation(value = "Armazena o registro do aluno.", notes = "Armazena o registro do aluno na base de dados.")
    public void create(@RequestBody Aluno aluno,HttpServletResponse response) {    
        log.trace("Criando aluno {}", aluno);
        aluno.setId(null);
        aluno = service.save(aluno);
        response.addHeader(HttpHeaders.LOCATION,"/api/alunos/" + aluno.getId());
    }        
```

##### @ApiModel e @ApiModelProperty

```java
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Aluno", description = "Informações de aluno")
public class Aluno {

    @ApiModelProperty(value = "Identificador do aluno", name = "id", dataType = "integer")
    private Integer id;

    @ApiModelProperty(value = "Nome do aluno", name = "nome", dataType = "string")
    private String nome;
```

#### Padrão da Informação

Descrição dos tipos de dados e semântica utilizadas na comunicação entre os sistemas. As informações transmitidas entre os sistemas não devem utilizar máscaras em sua representação.

```
    "/alunos/{id}": {
      "get": {
        "tags": [
          "Query em Aluno"
        ],
        "summary": "Obtem o aluno.",
        "description": "Obtem o aluno a partir do ID.",
        "operationId": "consultar",
        "produces": [
          "application/json"
        ],
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "type": "integer",
            "format": "int32"
          }
        ],
        "responses": {
          "200": {
            "description": "OK.",
            "schema": {
              "$ref": "#/definitions/Aluno"
            }
          },
          "404": {
            "description": "Aluno não encontrado."
          },
          "500": {
            "description": "Erro interno."
          }
        },
        "x-mask": {
          "nascimento": "dd/MM/yyyy"
        }
      }
    }
  }
```

#### Estrutura do Projeto: pin-goias

Contém as classes de serviços para acesso aos dados de domínio da aplicação.
Contém as classes de definição dos serviços REST e sua documentação utilizando o Swagger 2.0.
contém uma interface fornecida pelo framework Swagger para acesso à documentação dos serviços REST disponibilizados, além de fornecer uma API para testes dos serviços.


