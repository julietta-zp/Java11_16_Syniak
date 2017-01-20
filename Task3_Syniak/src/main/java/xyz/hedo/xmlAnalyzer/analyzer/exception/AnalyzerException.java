package xyz.hedo.xmlAnalyzer.analyzer.exception;

/**
 * Created by panic on 17.1.17.
 */
public class AnalyzerException extends Exception {
    private static final long serialVersionUID = 1L;

    public AnalyzerException(){
        super();
    }

    public AnalyzerException(String message){
        super(message);
    }

    public AnalyzerException(Exception e){
        super(e);
    }

    public AnalyzerException(String message, Exception e){
        super(message, e);
    }
}
