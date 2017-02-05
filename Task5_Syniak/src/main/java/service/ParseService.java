package service;

import bean.ParserType;
import bean.WebAppConfig;
import service.exception.ServiceException;

/**
 * Parse service interface
 */
public interface ParseService {

    /**
     * initiates parsing
     * @param xmlUri uri of xml file
     * @param parserType {@link ParserType}
     * @throws ServiceException
     */
    void parse(String xmlUri, ParserType parserType) throws ServiceException;

    /**
     * returns WebAppConfig object that was created out of xml file
     * @return {@link WebAppConfig}
     */
    WebAppConfig getWebAppConfig();
}
