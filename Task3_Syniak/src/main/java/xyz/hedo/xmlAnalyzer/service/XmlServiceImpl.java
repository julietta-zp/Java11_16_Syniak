package xyz.hedo.xmlAnalyzer.service;

import xyz.hedo.xmlAnalyzer.dao.DaoFactory;
import xyz.hedo.xmlAnalyzer.dao.exception.DaoException;
import xyz.hedo.xmlAnalyzer.analyzer.NodeInfo;
import xyz.hedo.xmlAnalyzer.analyzer.XmlAnalyzer;
import xyz.hedo.xmlAnalyzer.analyzer.exception.AnalyzerException;
import xyz.hedo.xmlAnalyzer.service.exception.ServiceException;

import java.io.IOException;

/**
 * Service to analyze xml document
 */
public class XmlServiceImpl implements XmlService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * analyzes xml document
     * @param fileName is a name of file to parse
     * @throws ServiceException
     */
    @Override
    public void parse(String fileName) throws ServiceException{

        try{

            String filePath = daoFactory.getXmlDAO().getAbsolutePath(fileName);

            try(XmlAnalyzer xmlAnalyzer = new XmlAnalyzer()){
                xmlAnalyzer.parse(filePath);
                NodeInfo node;
                while ((node = xmlAnalyzer.next()) != null){
                    // if it is a text content
                    if (node.isText()){
                        // then print node's info
                        System.out.println(node.getNodeType() + " " + node.getNodeContent());
                    }

                    /*// if it is an open tag
                    if (node.isOpenTag()){
                        // then print node's info
                        System.out.println(node.getNodeType() + " " + node.getNodeContent());
                    }*/
                }
            }catch (AnalyzerException | IOException e){
                throw new ServiceException(e);
            }

        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }
}
