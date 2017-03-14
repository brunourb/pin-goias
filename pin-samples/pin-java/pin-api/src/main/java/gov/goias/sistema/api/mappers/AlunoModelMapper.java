package gov.goias.sistema.api.mappers;

import gov.goias.sistema.api.view.model.Aluno;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import static org.dozer.loader.api.TypeMappingOptions.mapId;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;

public class AlunoModelMapper {

    public static DozerBeanMapper getMapper() {

        final DozerBeanMapper mapper = new DozerBeanMapper();
        final BeanMappingBuilder b = new BeanMappingBuilder() {
            protected void configure() {

                mapping(Aluno.class, gov.goias.sistema.entidades.Aluno.class,
                        TypeMappingOptions.oneWay(),
                        mapId("A"),
                        mapNull(true)
                );

                mapping(gov.goias.sistema.entidades.Aluno.class, Aluno.class,
                        TypeMappingOptions.oneWay(),
                        mapId("B"),
                        mapNull(true)
                )
                ;
            }
        };

        mapper.addMapping(b);
        return mapper;
    }


}
