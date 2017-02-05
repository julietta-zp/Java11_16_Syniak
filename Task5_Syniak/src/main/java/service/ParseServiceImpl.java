package service;

import bean.ParserType;
import bean.WebAppConfig;
import service.exception.ServiceException;
import service.strategy.dom.DomParseStrategy;
import service.strategy.ParseStrategy;
import service.strategy.sax.SaxParseStrategy;
import service.strategy.stax.StaxParseStrategy;

/**
 * Parse Service implementation where one of the parsers can be chosen
 * depending on {@link ParserType}
 */
public class ParseServiceImpl implements ParseService {

    private ParseStrategy strategy;

    @Override
    public void parse(String xmlUri, ParserType parserType) throws ServiceException{

        switch (parserType) {
            case SAX:
                strategy = new SaxParseStrategy();
                break;
            case STAX:
                strategy = new StaxParseStrategy();
                break;
            case DOM:
                strategy = new DomParseStrategy();
                break;
            default:
                throw new ServiceException("No such parser exists");
        }

        strategy.parse(xmlUri);
    }

    @Override
    public WebAppConfig getWebAppConfig() {
        return strategy.getWebAppConfig();
    }
}
