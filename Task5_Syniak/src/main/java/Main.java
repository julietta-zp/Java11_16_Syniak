import bean.*;
import service.ParseService;
import service.ParseServiceImpl;
import service.exception.ServiceException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String... args){

        /**
         * validates xml file
         */
        File schemaFile = new File("src/main/resources/web.xsd");
        Source xmlFile = new StreamSource(new File("src/main/resources/web.xml"));

        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (org.xml.sax.SAXException | IOException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
        }


        /**
         * xml file parsing
         */
        ParseService service = new ParseServiceImpl();
        try {

            service.parse("src/main/resources/web.xml", ParserType.SAX);
//            service.parse("src/main/resources/web.xml", ParserType.STAX);
//            service.parse("src/main/resources/web.xml", ParserType.DOM);
            WebAppConfig webAppConfig = service.getWebAppConfig();

            StringBuilder sb = new StringBuilder();
            sb.append("************ WebApp: ************");
            sb.append(System.lineSeparator())
                    .append("webApp id: ").append(webAppConfig.getId())
                    .append("; webApp version: ").append(webAppConfig.getVersion())
                    .append("; webApp display-name: ").append(webAppConfig.getDisplayName())
                    .append(System.lineSeparator());

            sb.append("************ Welcome-file-list: ************");
            for (String item : webAppConfig.getWelcomeFileList()){
                sb.append(System.lineSeparator()).append(item);
            }

            sb.append(System.lineSeparator());
            sb.append("************ Filter config: ************");
            for (FilterConfig f : webAppConfig.getFilterConfigList()){

                sb.append(System.lineSeparator()).append("filter-name: ").append(f.getFilterName())
                        .append("; filter-class: ").append(f.getFilterClass());

                for (String item : f.getFilterInitParamMap().keySet()){
                    sb.append(System.lineSeparator()).append("init-parameters: ")
                            .append("param-name: ").append(item)
                            .append("; param-value: ").append(f.getFilterInitParamMap().get(item));
                }
                for (String item : f.getFilterUrlDispatcherMapping().keySet()){
                    sb.append(System.lineSeparator()).append("url-pattern: ").append(item)
                            .append("; dispatcher: ").append(f.getFilterUrlDispatcherMapping().get(item));
                }
            }

            sb.append(System.lineSeparator());
            sb.append("************ Listener-list: ************");
            for (String item : webAppConfig.getListenerClassList()){
                sb.append(System.lineSeparator()).append("listener-class: ").append(item);
            }

            sb.append(System.lineSeparator());
            sb.append("************ Servlet config: ************");
            for (ServletConfig s : webAppConfig.getServletConfigList()){
                sb.append(System.lineSeparator()).append("servlet name: ").append(s.getServletName())
                        .append(" servlet class: ").append(s.getServletClass())
                        .append(" url-pattern: ").append(s.getUrlPattern());
            }

            sb.append(System.lineSeparator());
            sb.append("************ Error page config: ************");
            for (ErrorPageConfig e : webAppConfig.getErrorPageConfigList()){
                if (e.getErrorCode() != 0){
                    sb.append(System.lineSeparator()).append("error code: ").append(e.getErrorCode())
                            .append(" location: ").append(e.getLocation());
                } else {
                    sb.append(System.lineSeparator()).append("exception type: ").append(e.getExceptionType())
                            .append(" location: ").append(e.getLocation());
                }
            }

            System.out.println(sb.toString());

        }catch (ServiceException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
