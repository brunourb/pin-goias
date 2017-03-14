package gov.goias.sistema.api.view.model;

import io.swagger.annotations.*;

import java.util.Date;

@ApiModel(value = "Aluno", description = "Informações de aluno")
public class Aluno {

    @ApiModelProperty(value = "Identificador do aluno", name = "id", dataType = "integer")
    private Integer id;

    @ApiModelProperty(value = "Nome do aluno", name = "nome", dataType = "string")
    private String nome;

    @ApiModelProperty(value = "Data de nascimento do aluno", name = "nascimento", dataType = "date")
    private Date nascimento;

    @ApiModelProperty(value = "Sexo do aluno (1 - Masculino, 2 - Feminino)", name = "sexo", dataType = "integer", allowableValues = "1, 2")
    private String sexo;

    @ApiModelProperty(value = "Indica se o registro do aluno está ativo", name = "ativo", dataType = "boolean")
    private Boolean ativo;

    public Aluno() {
    }

    public Aluno(final Integer id, final String nome, final Date nascimento, final String sexo, final Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public void setNascimento(final Date data) {
        this.nascimento = data;
    }

    public void setSexo(final String sexo) {
        this.sexo = sexo;
    }

    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }
}
