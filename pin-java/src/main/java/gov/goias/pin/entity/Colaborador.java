package gov.goias.pin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

/**
  Entidade que representa um colaborador
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Colaborador", description = "Informações de colaborador")
public class Colaborador {

    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "nome", length = 80, nullable = false)
    @ApiModelProperty(value = "Nome do colaborador", allowableValues = "range[1, 80]")
    private String nome;

    @Email
    @NotNull
    @Column(name = "email", length = 50, nullable = false)
    @ApiModelProperty(value = "E-mail do colaborador", allowableValues = "range[1, 50]")
    private String email;

    @CPF
    @NotNull
    @NotEmpty
    @Column(name = "cpf", length = 11,nullable = false)
    @ApiModelProperty(value = "Cpf do colaborador", allowableValues = "range[11, 11]")
    private String cpf;

    @Past
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "data_nascimento", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @ApiModelProperty(value = "Data de nascimento do colaborador")
    private LocalDate dataNascimento;

    @NotNull
    @ApiModelProperty(value = "Sexo do colaborador")
    @Column(name = "sexo", length = 1, nullable = false)
    private Sexo sexo;

    @NotNull
    @ManyToOne
    private UnidadeAdministrativa unidade;

    /**
     * Enumerador que representa o sexo de um colaborador
     */
    @AllArgsConstructor
    public enum Sexo {
        MASCULINO("M"), FEMININO("F");

        @Getter
        private String sigla;

        public static Sexo toEnum(String sigla){
            switch (sigla){
                case "M" :
                    return Sexo.MASCULINO;
                case "F" :
                    return Sexo.FEMININO;
                default:
                    throw new IllegalArgumentException("Valor da sigla de Enumerador Sexo inválido");
            }
        }

    }

}