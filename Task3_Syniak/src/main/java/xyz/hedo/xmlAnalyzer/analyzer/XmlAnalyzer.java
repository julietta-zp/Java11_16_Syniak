package xyz.hedo.xmlAnalyzer.analyzer;

import xyz.hedo.xmlAnalyzer.analyzer.exception.AnalyzerException;
import xyz.hedo.xmlAnalyzer.analyzer.exception.XmlParserException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Class analyzes xml file, extracts nodes
 */
public class XmlAnalyzer implements Closeable{

    private XmlParser xmlParser;

    public XmlAnalyzer() {

    }

    /**
     * gets next node from XmlParser
     * @return {@link NodeInfo}
     * @throws AnalyzerException
     */
    public NodeInfo next() throws AnalyzerException {
        NodeInfo node;
        try{
            node = xmlParser.next();
        }catch (XmlParserException e){
            throw new AnalyzerException(e);
        }
        return node;
    }

    /**
     * initializes XmlParser
     * @param pathName is a path name of xml file
     * @throws AnalyzerException
     */
    public void parse(String pathName) throws AnalyzerException {
        if (pathName == null || pathName.isEmpty()) {
            throw new AnalyzerException("Path name cannot be empty or null");
        }

        File xmlFile = new File(pathName);
        try{
            xmlParser = new XmlParserImpl();
            xmlParser.init(xmlFile);
        }catch (XmlParserException e){
            throw new AnalyzerException(e);
        }
    }

    /**
     * closes the stream
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (xmlParser != null){
            xmlParser.close();
        }
    }
}
