package gov.goias.sistema.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "TBL_ALUNO")
public class Aluno {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="NOME")
    @NotNull
    private String nome;

    @Column(name="NASCIMENTO")
    @NotNull
    private Date nascimento;

    @Column(name="SEXO")
    private String sexo;

    @Column(name="ATIVO")
    private Boolean ativo;

    public Aluno(){}

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

    public String getSituacao() {
        return ativo? "Ativo" : "Inativo";
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