package xyz.hedo.xmlAnalyzer.service;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private XmlService xmlService = new XmlServiceImpl();

    private ServiceFactory() {
    }

    public XmlService getXmlService() {
        return xmlService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
