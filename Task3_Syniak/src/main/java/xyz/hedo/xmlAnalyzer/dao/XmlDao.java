package xyz.hedo.xmlAnalyzer.dao;

import xyz.hedo.xmlAnalyzer.dao.exception.DaoException;

public interface XmlDao {

    String getAbsolutePath(String fileName) throws DaoException;

}
