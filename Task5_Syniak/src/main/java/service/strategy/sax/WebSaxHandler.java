package service.strategy.sax;

import bean.ErrorPageConfig;
import bean.FilterConfig;
import bean.ServletConfig;
import bean.WebAppConfig;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import service.strategy.WebTagName;

import java.util.ArrayList;
import java.util.List;

/**
 * SaxHandler for web.xml
 */
public class WebSaxHandler extends DefaultHandler {

    private static final String WEB_APP_ATTRIBUTE_ID = "id";
    private static final String WEB_APP_ATTRIBUTE_VERSION = "version";
    private WebAppConfig webAppConfig;

    private List<String> welcomeFileList;
    private List<FilterConfig> filterConfigList;
    private List<String> listenerClassList;
    private List<ServletConfig> servletConfigList;
    private List<ErrorPageConfig> errorPageConfigList;

    private FilterConfig filterConfig;
    private ServletConfig servletConfig;
    private ErrorPageConfig errorPageConfig;

    private String initParamName;
    private String initParamValue;
    private String urlPattern;
    private String dispatcher;
    private WebTagName currentParentTagName;
    private StringBuilder text;


    public WebAppConfig getWebAppConfig(){
        return this.webAppConfig;
    }

    @Override
    public void startDocument() throws SAXException {
        webAppConfig = new WebAppConfig();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        text = new StringBuilder();
        WebTagName tagName = WebTagName.getElementWebTagName(qName);
        switch (tagName){
            case WEB_APP:
                webAppConfig.setId(attributes.getValue(WEB_APP_ATTRIBUTE_ID));
                webAppConfig.setVersion(attributes.getValue(WEB_APP_ATTRIBUTE_VERSION));
                filterConfigList = new ArrayList<>();
                listenerClassList = new ArrayList<>();
                servletConfigList = new ArrayList<>();
                errorPageConfigList = new ArrayList<>();
                break;
            case WELCOME_FILE_LIST:
                welcomeFileList = new ArrayList<>();
                break;
            case FILTER:
                filterConfig = new FilterConfig();
                currentParentTagName = WebTagName.FILTER;
                break;
            case SERVLET:
                servletConfig = new ServletConfig();
                currentParentTagName = WebTagName.SERVLET;
                break;
            case SERVLET_MAPPING:
                currentParentTagName = WebTagName.SERVLET_MAPPING;
                break;
            case ERROR_PAGE:
                errorPageConfig = new ErrorPageConfig();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        WebTagName tagName = WebTagName.getElementWebTagName(qName);
        switch (tagName){
            case DISPLAY_NAME:
                webAppConfig.setDisplayName(text.toString());
                break;
            case WELCOME_FILE_LIST:
                webAppConfig.setWelcomeFileList(welcomeFileList);
                break;
            case WELCOME_FILE:
                welcomeFileList.add(text.toString());
                break;
            case FILTER:
                filterConfigList.add(filterConfig);
                break;
            case FILTER_NAME:
                if (currentParentTagName == WebTagName.FILTER){
                    filterConfig.setFilterName(text.toString());
                } else if (currentParentTagName == WebTagName.FILTER_MAPPING){
                    for (FilterConfig item : filterConfigList) {
                        if (item.getFilterName().equals(text.toString())){
                            filterConfig = item;
                        }
                    }
                }
                break;
            case FILTER_CLASS:
                filterConfig.setFilterClass(text.toString());
                break;
            case INIT_PARAM:
                if (currentParentTagName == WebTagName.FILTER){
                    filterConfig.addFilterInitParamItem(initParamName, initParamValue);
                } else if (currentParentTagName == WebTagName.SERVLET){
                    servletConfig.addServletInitParamItem(initParamName, initParamValue);
                }
                break;
            case PARAM_NAME:
                initParamName = text.toString();
                break;
            case PARAM_VALUE:
                initParamValue = text.toString();
                break;
            case FILTER_MAPPING:
                filterConfig.addFilterUrlDispatcherMappingItem(urlPattern, dispatcher);
                break;
            case URL_PATTERN:
                urlPattern = text.toString();
                break;
            case DISPATCHER:
                dispatcher = text.toString();
                break;
            case LISTENER_CLASS:
                listenerClassList.add(text.toString());
                break;
            case SERVLET:
                servletConfigList.add(servletConfig);
                break;
            case SERVLET_NAME:
                if (currentParentTagName != WebTagName.SERVLET_MAPPING){
                    servletConfig.setServletName(text.toString());
                } else {
                    for (ServletConfig item : servletConfigList){
                        if (item.getServletName().equals(text.toString())){
                            servletConfig = item;
                        }
                    }
                }
                break;
            case SERVLET_CLASS:
                servletConfig.setServletClass(text.toString());
                break;
            case SERVLET_MAPPING:
                servletConfig.setUrlPattern(urlPattern);
                break;
            case ERROR_PAGE:
                errorPageConfigList.add(errorPageConfig);
                break;
            case EXCEPTION_TYPE:
                errorPageConfig.setExceptionType(text.toString());
                break;
            case ERROR_CODE:
                errorPageConfig.setErrorCode(Integer.parseInt(text.toString()));
                break;
            case LOCATION:
                errorPageConfig.setLocation(text.toString());
                break;
            case WEB_APP:
                webAppConfig.setFilterConfigList(filterConfigList);
                webAppConfig.setServletConfigList(servletConfigList);
                webAppConfig.setListenerClassList(listenerClassList);
                webAppConfig.setErrorPageConfigList(errorPageConfigList);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }
}
