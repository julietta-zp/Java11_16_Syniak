package xyz.hedo.xmlAnalyzer.service;

import xyz.hedo.xmlAnalyzer.DAO.DAOFactory;
import xyz.hedo.xmlAnalyzer.DAO.exception.DAOException;
import xyz.hedo.xmlAnalyzer.analyzer.NodeInfo;
import xyz.hedo.xmlAnalyzer.analyzer.XmlAnalyzer;
import xyz.hedo.xmlAnalyzer.analyzer.exception.AnalyzerException;
import xyz.hedo.xmlAnalyzer.service.exception.ServiceException;

import java.io.IOException;

/**
 * Service to analyze xml document
 */
public class XmlServiceImpl implements XmlService {

    private DAOFactory daoFactory = DAOFactory.getInstance();

    /**
     * analyzes xml document
     * @param fileName is a name of file to parse
     * @throws ServiceException
     */
    @Override
    public void parse(String fileName) throws ServiceException{

        try{

            String filePath = daoFactory.getXmlDAO().getAbsolutePath(fileName);

            try(XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(filePath)){
                NodeInfo node;
                while ((node = xmlAnalyzer.next()) != null){
                    // if it is a text content
                    if (node.isContent()){
                        // then print node's info
                        System.out.println(node.getNodeType() + " " + node.getNodeContent());
                    }
                }
            }catch (AnalyzerException | IOException e){
                throw new ServiceException(e);
            }

        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
