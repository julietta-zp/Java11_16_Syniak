package service.strategy.sax;

import bean.WebAppConfig;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import service.exception.ServiceException;
import service.strategy.ParseStrategy;

import java.io.IOException;

/**
 * Parse using SAX parser
 */
public class SaxParseStrategy implements ParseStrategy {

    private WebSaxHandler handler;

    @Override
    public void parse(String xmlUri) throws ServiceException {

        try {

            XMLReader reader = XMLReaderFactory.createXMLReader();
            handler = new WebSaxHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(xmlUri));

        }catch (SAXException | IOException e ){

            throw new ServiceException(e);

        }

    }

    @Override
    public WebAppConfig getWebAppConfig() {
        return handler.getWebAppConfig();
    }
}
