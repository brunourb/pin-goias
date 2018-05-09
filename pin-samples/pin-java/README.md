# Padrão de interoperabilidade de serviços - Goiás: Implementação de Referência utilizando a tecnologia Java

## Documentação PIN
	
[Padrão de Interoperabilidade](ATUALIZAR URL...Interoperabilidade.odt)
		

#### Principais Tecnologias

Java 8, Spring Fox, Swagger 2.0 (OpenAPI)

#### OpenAPI

A especificação OpenAPI foi criada por um consórcio, a Open API Iniciative (OAI), formado por especialistas que reconhecem a importância da padronização de como as APIs REST são descritas. Várias grandes empresas são membros deste consórcio, entre elas, a IBM, Google, Microsoft, SmartBear (que doou a especificação do Swagger 2.0 como base para a primeira versão da especificação OpenAPI), PayPal, entre outros.

A especificação utilizada está disponível em: https://swagger.io/docs/specification/2-0/adding-examples/

#### Swagger 2.0

A implementação da especificação utilizada foi o framework Swagger 2.0, que fornece um conjunto de bibliotecas e anotações para a geração da documentação da API durante a implementação do serviço.

Observação: Para API que serão consumidas pela ferramenta WSO2 - API Manager, utilizar somente a especificação Swagger 2. Não utilizar a especificação Open Api 3.0, pois o WSO2 - API Manager, ainda não lê swagger nesta versão/especifica 3.0

#### Maven dependências

	<dependencies>
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
    @RequestMapping("/colaborador")
    @Api(value = "Operações de CRUD em Colaborador")
    public class ColaboradorCmds {
```

##### @ApiOperation

```java
    /**
     * Armazena um Colaborador no sistema.
     *
     * @param colaborador Informações do colaborador
     */
    @PostMapping(path = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Armazena o registro do colaborador.", notes = "Armazena o registro do colaborador na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Criado."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public ResponseEntity<Colaborador> salvar(@RequestBody Colaborador colaborador) {
        Colaborador save = service.save( colaborador );
        return new ResponseEntity<>( save, HttpStatus.OK );
    }
```

##### @ApiModel e @ApiModelProperty


```java
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Colaborador", description = "Informações de colaborador")
public class Colaborador {

    @ApiModelProperty(value = "Identificador do colaborador")
    private Integer id;

    @ApiModelProperty(value = "Nome do colaborador")
    private String nome;
```

#### Padrão da Informação

Descrição dos tipos de dados e semântica utilizadas na comunicação entre os sistemas. As informações transmitidas entre os sistemas não devem utilizar máscaras em sua representação.

```
    "/colaborador/{id}": {
      "get": {
        "tags": [
          "Query em Colaborador"
        ],
        "summary": "Obtem o colaborador.",
        "description": "Obtem o colaborador a partir do ID.",
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
              "$ref": "#/definitions/Colaborador"
            }
          },
          "404": {
            "description": "Colaborador não encontrado."
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

#### Estrutura do Projeto: pin-java

Este é o pin-java representação do pin-goias com a linguagem Java. 

##### Pacotes que demonstram a criação de serviços no padrão PIN
Os pacotes gov.goias.pin e gov.goias.pin.resource contém as classes de definição dos serviços REST e sua documentação utilizando o Swagger 2.0.
A existência dessas classes gera automaticamente uma interface fornecida pelo framework Swagger para acesso à documentação dos serviços REST disponibilizados, além de fornecer uma API para testes dos serviços.

##### Outros pacotes
Contém as classes de exemplo dos serviços para acesso aos dados de domínio da aplicação utilizando spring-jpa




