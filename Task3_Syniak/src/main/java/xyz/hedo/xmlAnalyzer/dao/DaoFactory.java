package xyz.hedo.xmlAnalyzer.dao;

public class DaoFactory {
    private static final DaoFactory daoFactory = new DaoFactory();

    private DaoFactory() {
    }

    private XmlDao xmlDAO = new XmlDaoImpl();

    public XmlDao getXmlDAO() {
        return xmlDAO;
    }

    public static DaoFactory getInstance() {
        return daoFactory;
    }
}
