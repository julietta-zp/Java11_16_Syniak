package service.strategy;

import bean.WebAppConfig;
import service.exception.ServiceException;

/**
 * Parse strategy interface
 */
public interface ParseStrategy {

    /**
     * initiates parsing
     * @param xmlUri uri of xml file
     * @throws ServiceException
     */
    void parse(String xmlUri) throws ServiceException;

    /**
     * returns WebAppConfig object that was created out of xml file
     * @return {@link WebAppConfig}
     */
    WebAppConfig getWebAppConfig();
}
