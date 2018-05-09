package gov.goias.pin.entity.converter;

import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Conversor de LocalDate para Date e vice versa no repositório
 * JPA 2.1 foi liberado antes do Java 8 e a API java.time.* simplesmente não existia naquela época.
 * Portanto a anotação @Temporal pode apenas ser aplicada a atributos do tipo <code>java.util.Date</code> e <code>java.util.Calendar</code>.
 */
@Log4j2
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        log.trace("Convertendo Localdate para Date {}",date);
        return date == null ? null : Date.valueOf(date);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        log.trace("Convertendo Date para Localdate {}", date);
        return date == null ? null : date.toLocalDate();
    }

}