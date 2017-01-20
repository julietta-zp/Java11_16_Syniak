package xyz.hedo.xmlAnalyzer.DAO;

import xyz.hedo.xmlAnalyzer.DAO.exception.DAOException;

public interface XmlDAO {

    String getAbsolutePath(String fileName) throws DAOException;

}
