package xyz.hedo.xmlAnalyzer.analyzer.exception;

/**
 * Created by panic on 17.1.17.
 */
public class XmlParserException extends Exception {
    private static final long serialVersionUID = 1L;

    public XmlParserException(){
        super();
    }

    public XmlParserException(String message){
        super(message);
    }

    public XmlParserException(Exception e){
        super(e);
    }

    public XmlParserException(String message, Exception e){
        super(message, e);
    }
}
