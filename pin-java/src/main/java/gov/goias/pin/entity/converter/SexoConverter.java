package gov.goias.pin.entity.converter;

import gov.goias.pin.entity.Colaborador;
import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Conversor do enumerador Sexo para a sigla no repositório e vice versa.
 */
@Log4j2
@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Colaborador.Sexo, String> {

    @Override
    public String convertToDatabaseColumn(Colaborador.Sexo sexo) {
        log.trace("Convertendo enum Sexo para Sigla {}",sexo);
        return sexo.getSigla();
    }

    @Override
    public Colaborador.Sexo convertToEntityAttribute(String sigla) {
        log.trace("Convertendo Sigla de sexo para enum Sexo {}",sigla);
        return Colaborador.Sexo.toEnum(sigla);
    }

}