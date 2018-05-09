package gov.goias.pin.entity.converter;

import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Conversor de LocalDateTime para Timestamp e vice versa no repositório
 * JPA 2.1 foi liberado antes do Java 8 e a API java.time.* simplesmente não existia naquela época.
 * Portanto a anotação @Temporal pode apenas ser aplicada a atributos do tipo <code>java.util.Date</code> e <code>java.util.Calendar</code>.
 */
@Log4j2
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime dateTime) {
        log.trace("Convertendo Timestamp para LocalDateTime {}",dateTime);
        return (dateTime == null ? null : Timestamp.valueOf(dateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        log.trace("Convertendo Timestamp para LocaldateTime {}", timestamp);
        return (timestamp == null ? null : timestamp.toLocalDateTime());
    }
}