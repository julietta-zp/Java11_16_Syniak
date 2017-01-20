package xyz.hedo.xmlAnalyzer.analyzer;

import xyz.hedo.xmlAnalyzer.analyzer.exception.XmlParserException;

import java.io.File;
import java.io.IOException;

/**
 * Parser finds {@link NodeInfo} elements in xml file lines
 */

public interface XmlParser{

    void init(File file) throws XmlParserException;
    void startDocument();
    void endDocument();
    NodeInfo next() throws XmlParserException;
    boolean hasNext() throws XmlParserException;
    void close() throws IOException;
}
