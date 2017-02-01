package xyz.hedo.xmlAnalyzer.dao;

import xyz.hedo.xmlAnalyzer.dao.exception.DaoException;

import java.net.URL;

public class XmlDaoImpl implements XmlDao {

    public String getAbsolutePath(String fileName) throws DaoException {
        if (fileName.isEmpty()){
            throw new DaoException("File name cannot be empty or null");
        }
        URL url = getClass().getClassLoader().getResource(fileName);
        return url.getPath();
    }

}
