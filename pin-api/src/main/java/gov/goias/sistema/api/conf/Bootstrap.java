package gov.goias.sistema.api.conf;

import io.swagger.config.ConfigFactory;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.Swagger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Bootstrap extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();

        // Inicializa o Swagger
        Info info = new Info()
                .title("Padrão de Interoperabilidade - Goiás")
                .description("Padrão de interoperabilidade para os serviços disponibilizados.")
                .termsOfService("")
                .contact(new Contact()
                        .email("projetos.scti@segplan.go.gov.br")).version("1.0.0");

        Swagger swagger = new Swagger().info(info);
        swagger.setBasePath(context.getContextPath() + "/api");

        new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
    }
}
