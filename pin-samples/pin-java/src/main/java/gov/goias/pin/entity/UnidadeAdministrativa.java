package gov.goias.pin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entidade que representa uma Unidade Administrativa
 */
@Data
//@Table(name = "unidade")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Unidade Administrativa", description = "Unidade Administrativa a qual o Colaborador está vinculado.")
public class UnidadeAdministrativa {

    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "nome", length = 80, nullable = false)
    @ApiModelProperty(value = "Nome da Unidade Administrativa", allowableValues = "range[1, 80]")
    private String nome;

}