package xyz.hedo.xmlAnalyzer.DAO;

import xyz.hedo.xmlAnalyzer.DAO.exception.DAOException;

import java.net.URL;

public class XmlDAOImpl implements XmlDAO {

    public String getAbsolutePath(String fileName) throws DAOException{
        if (fileName.isEmpty()){
            throw new DAOException("File name cannot be empty or null");
        }
        URL url = getClass().getClassLoader().getResource(fileName);
        return url.getPath();
    }

}
