package xyz.hedo.xmlAnalyzer.DAO;

public class DAOFactory {
    private static final DAOFactory daoFactory = new DAOFactory();

    private DAOFactory() {
    }

    private XmlDAO xmlDAO = new XmlDAOImpl();

    public XmlDAO getXmlDAO() {
        return xmlDAO;
    }

    public static DAOFactory getInstance() {
        return daoFactory;
    }
}
