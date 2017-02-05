package service.strategy.stax;

import bean.ErrorPageConfig;
import bean.FilterConfig;
import bean.ServletConfig;
import bean.WebAppConfig;
import service.exception.ServiceException;
import service.strategy.ParseStrategy;
import service.strategy.WebTagName;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse Strategy using StAX parser
 */
public class StaxParseStrategy implements ParseStrategy {

    private static final String WEB_APP_ATTRIBUTE_ID = "id";
    private static final String WEB_APP_ATTRIBUTE_VERSION = "version";
    private WebAppConfig webAppConfig;

    @Override
    public void parse(String xmlUri) throws ServiceException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            InputStream input = new FileInputStream(xmlUri);
            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
            webAppConfig = process(reader);
        }catch (FileNotFoundException | XMLStreamException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public WebAppConfig getWebAppConfig() {
        return webAppConfig;
    }

    private static WebAppConfig process(XMLStreamReader reader) throws XMLStreamException {
        WebAppConfig webAppConfig = new WebAppConfig();

        List<String> welcomeFileList = new ArrayList<>();
        List<FilterConfig> filterConfigList = new ArrayList<>();
        List<String> listenerClassList = new ArrayList<>();
        List<ServletConfig> servletConfigList = new ArrayList<>();
        List<ErrorPageConfig> errorPageConfigList = new ArrayList<>();

        ServletConfig servletConfig = null;
        FilterConfig filterConfig = null;
        ErrorPageConfig errorPageConfig = null;

        WebTagName webTagName = null;
        WebTagName parentTagName = null;

        String initParamName = null;
        String initParamValue = null;
        String urlPattern = null;
        String dispatcher = null;

        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    webTagName = WebTagName.getElementWebTagName(reader.getLocalName());
                    switch (webTagName){
                        case WEB_APP:
                            webAppConfig.setId(reader.getAttributeValue(null, WEB_APP_ATTRIBUTE_ID));
                            webAppConfig.setVersion(reader.getAttributeValue(null, WEB_APP_ATTRIBUTE_VERSION));
                            break;
                        case FILTER:
                            filterConfig = new FilterConfig();
                            parentTagName = WebTagName.FILTER;
                            break;
                        case FILTER_MAPPING:
                            parentTagName = WebTagName.FILTER_MAPPING;
                            break;
                        case SERVLET:
                            servletConfig = new ServletConfig();
                            parentTagName = WebTagName.SERVLET;
                            break;
                        case SERVLET_MAPPING:
                            parentTagName = WebTagName.SERVLET_MAPPING;
                            break;
                        case ERROR_PAGE:
                            errorPageConfig = new ErrorPageConfig();
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty()) {
                        break;
                    }
                    switch (webTagName) {
                        case DISPLAY_NAME:
                            webAppConfig.setDisplayName(text);
                            break;
                        case WELCOME_FILE:
                            welcomeFileList.add(text);
                            break;
                        case FILTER_NAME:
                            if (parentTagName == WebTagName.FILTER){
                                filterConfig.setFilterName(text);
                            } else if (parentTagName == WebTagName.FILTER_MAPPING){
                                for (FilterConfig item : filterConfigList) {
                                    if (item.getFilterName().equals(text)){
                                        filterConfig = item;
                                    }
                                }
                            }
                            break;
                        case FILTER_CLASS:
                            filterConfig.setFilterClass(text);
                            break;
                        case PARAM_NAME:
                            initParamName = text;
                            break;
                        case PARAM_VALUE:
                            initParamValue = text;
                            break;
                        case URL_PATTERN:
                            urlPattern = text;
                            break;
                        case DISPATCHER:
                            dispatcher = text;
                            break;
                        case LISTENER_CLASS:
                            listenerClassList.add(text);
                            break;
                        case SERVLET_NAME:
                            if (parentTagName != WebTagName.SERVLET_MAPPING){
                                servletConfig.setServletName(text);
                            } else {
                                for (ServletConfig item : servletConfigList){
                                    if (item.getServletName().equals(text)){
                                        servletConfig = item;
                                    }
                                }
                            }
                            break;
                        case SERVLET_CLASS:
                            servletConfig.setServletClass(text);
                            break;
                        case EXCEPTION_TYPE:
                            errorPageConfig.setExceptionType(text);
                            break;
                        case ERROR_CODE:
                            errorPageConfig.setErrorCode(Integer.parseInt(text));
                            break;
                        case LOCATION:
                            errorPageConfig.setLocation(text);
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    webTagName = WebTagName.getElementWebTagName(reader.getLocalName());
                    switch (webTagName){
                        case WEB_APP:
                            webAppConfig.setWelcomeFileList(welcomeFileList);
                            webAppConfig.setListenerClassList(listenerClassList);
                            webAppConfig.setFilterConfigList(filterConfigList);
                            webAppConfig.setServletConfigList(servletConfigList);
                            webAppConfig.setErrorPageConfigList(errorPageConfigList);
                            break;
                        case WELCOME_FILE_LIST:
                            webAppConfig.setWelcomeFileList(welcomeFileList);
                            break;
                        case FILTER:
                            filterConfigList.add(filterConfig);
                            break;
                        case INIT_PARAM:
                            if (parentTagName == WebTagName.FILTER){
                                filterConfig.addFilterInitParamItem(initParamName, initParamValue);
                            } else if (parentTagName == WebTagName.SERVLET){
                                servletConfig.addServletInitParamItem(initParamName, initParamValue);
                            }
                            break;
                        case FILTER_MAPPING:
                            filterConfig.addFilterUrlDispatcherMappingItem(urlPattern, dispatcher);
                            break;
                        case SERVLET:
                            servletConfigList.add(servletConfig);
                            break;
                        case SERVLET_MAPPING:
                            servletConfig.setUrlPattern(urlPattern);
                            break;
                        case ERROR_PAGE:
                            errorPageConfigList.add(errorPageConfig);
                            break;
                    }
                    break;
            }

        }
        return webAppConfig;
    }

}
