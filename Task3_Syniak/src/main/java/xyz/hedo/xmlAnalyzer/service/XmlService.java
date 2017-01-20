package xyz.hedo.xmlAnalyzer.service;

import xyz.hedo.xmlAnalyzer.service.exception.ServiceException;

public interface XmlService {

    void parse(String name) throws ServiceException;
}
