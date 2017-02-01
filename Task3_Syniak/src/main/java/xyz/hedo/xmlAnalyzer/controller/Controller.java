package xyz.hedo.xmlAnalyzer.controller;

import xyz.hedo.xmlAnalyzer.service.ServiceFactory;
import xyz.hedo.xmlAnalyzer.service.exception.ServiceException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static final Logger logger = Logger.getLogger(String.valueOf(Controller.class));
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * reads file and analyzes it
      * @param fileName is a name of document
     */
    public void readFile(String fileName) {

        try {

            serviceFactory.getXmlService().parse(fileName);

        } catch (ServiceException e){

            logger.log(Level.SEVERE, "an exception was thrown", e);

        }

    }
}
