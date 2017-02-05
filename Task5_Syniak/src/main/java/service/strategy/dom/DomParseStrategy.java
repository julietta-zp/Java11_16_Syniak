package service.strategy.dom;

import bean.ErrorPageConfig;
import bean.FilterConfig;
import bean.ServletConfig;
import bean.WebAppConfig;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import service.exception.ServiceException;
import service.strategy.ParseStrategy;
import service.strategy.WebTagName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse using DOM parser
 */
public class DomParseStrategy implements ParseStrategy {

    private static final String WEB_APP_ATTRIBUTE_ID = "id";
    private static final String WEB_APP_ATTRIBUTE_VERSION = "version";
    private WebAppConfig webAppConfig;

    @Override
    public void parse(String xmlUri) throws ServiceException {
        try {

            DOMParser parser = new DOMParser();
            parser.parse(xmlUri);
            Document document = parser.getDocument();
            webAppConfig = process(document);
        }catch (SAXException | IOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public WebAppConfig getWebAppConfig() {
        return webAppConfig;
    }

    private static WebAppConfig process(Document document){

        WebAppConfig webAppConfig = new WebAppConfig();

        List<String> welcomeFileList = new ArrayList<>();
        List<FilterConfig> filterConfigList = new ArrayList<>();
        List<String> listenerClassList = new ArrayList<>();
        List<ServletConfig> servletConfigList = new ArrayList<>();
        List<ErrorPageConfig> errorPageConfigList = new ArrayList<>();

        ServletConfig servletConfig;
        FilterConfig filterConfig;
        ErrorPageConfig errorPageConfig;

        Element root = document.getDocumentElement();

        webAppConfig.setId(root.getAttribute(WEB_APP_ATTRIBUTE_ID));
        webAppConfig.setVersion(root.getAttribute(WEB_APP_ATTRIBUTE_VERSION));

        NodeList displayNameNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.DISPLAY_NAME));
        webAppConfig.setDisplayName(displayNameNodes.item(0).getTextContent());

        NodeList welcomeFilesNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.WELCOME_FILE_LIST)).item(0).getChildNodes();
        for (int i = 0; i < welcomeFilesNodes.getLength(); i++)    {
            welcomeFileList.add(welcomeFilesNodes.item(i).getTextContent().trim());
        }

        NodeList filterNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.FILTER));
        for (int i = 0; i < filterNodes.getLength(); i++){
            filterConfig = new FilterConfig();
            Element filterElement = (Element) filterNodes.item(i);
            filterConfig.setFilterName(getSingleChild(filterElement, WebTagName.getElementStringTagName(WebTagName.FILTER_NAME)).getTextContent().trim());
            filterConfig.setFilterClass(getSingleChild(filterElement, WebTagName.getElementStringTagName(WebTagName.FILTER_CLASS)).getTextContent().trim());

            NodeList filterInitParamChildNodes = filterElement.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.INIT_PARAM));
            if (filterInitParamChildNodes.getLength() > 0){
                for (int j = 0; j < filterInitParamChildNodes.getLength(); j++){
                    Element initParamElement = (Element) filterInitParamChildNodes.item(j);
                    filterConfig.addFilterInitParamItem(
                            getSingleChild(initParamElement,WebTagName.getElementStringTagName(WebTagName.PARAM_NAME)).getTextContent().trim(),
                            getSingleChild(initParamElement, WebTagName.getElementStringTagName(WebTagName.PARAM_VALUE)).getTextContent().trim()
                    );
                }
            }
            filterConfigList.add(filterConfig);
        }

        NodeList filterMappingNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.FILTER_MAPPING));
        for (int i = 0; i < filterMappingNodes.getLength(); i++){
            Element filterMappingElement = (Element) filterMappingNodes.item(i);
            String filterName = getSingleChild(filterMappingElement, WebTagName.getElementStringTagName(WebTagName.FILTER_NAME)).getTextContent().trim();
            for (FilterConfig f : filterConfigList){
                if (f.getFilterName().equals(filterName)){
                    f.addFilterUrlDispatcherMappingItem(
                            getSingleChild(filterMappingElement, WebTagName.getElementStringTagName(WebTagName.URL_PATTERN)).getTextContent().trim(),
                            getSingleChild(filterMappingElement, WebTagName.getElementStringTagName(WebTagName.DISPATCHER)).getTextContent().trim()
                    );
                }
            }
        }

        NodeList listenerNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.LISTENER));
        for (int i = 0; i < listenerNodes.getLength(); i++){
            Element listenerElement = (Element) listenerNodes.item(i);

            listenerClassList.add(getSingleChild(listenerElement, WebTagName.getElementStringTagName(WebTagName.LISTENER_CLASS)).getTextContent().trim());
        }

        NodeList servletNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.SERVLET));
        for (int i = 0; i < servletNodes.getLength(); i++){
            Element servletElement = (Element) servletNodes.item(i);
            servletConfig = new ServletConfig();
            servletConfig.setServletName(getSingleChild(servletElement, WebTagName.getElementStringTagName(WebTagName.SERVLET_NAME)).getTextContent().trim());
            servletConfig.setServletClass(getSingleChild(servletElement, WebTagName.getElementStringTagName(WebTagName.SERVLET_CLASS)).getTextContent().trim());

            NodeList servletInitParamChildNodes = servletElement.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.INIT_PARAM));
            if (servletInitParamChildNodes.getLength() > 0){
                for (int j = 0; j < servletInitParamChildNodes.getLength(); j++){
                    Element initParamElement = (Element) servletInitParamChildNodes.item(j);
                    servletConfig.addServletInitParamItem(
                            getSingleChild(initParamElement, WebTagName.getElementStringTagName(WebTagName.PARAM_NAME)).getTextContent().trim(),
                            getSingleChild(initParamElement, WebTagName.getElementStringTagName(WebTagName.PARAM_VALUE)).getTextContent().trim()
                    );
                }
            }

            servletConfigList.add(servletConfig);
        }

        NodeList servletMappingNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.SERVLET_MAPPING));
        for (int i = 0; i < servletMappingNodes.getLength(); i++){
            Element servletMappingElement = (Element) servletMappingNodes.item(i);
            String servletName = getSingleChild(servletMappingElement, WebTagName.getElementStringTagName(WebTagName.SERVLET_NAME)).getTextContent().trim();
            for (ServletConfig s : servletConfigList){
                if (s.getServletName().equals(servletName)){
                    s.setUrlPattern(getSingleChild(servletMappingElement, WebTagName.getElementStringTagName(WebTagName.URL_PATTERN)).getTextContent().trim());
                }
            }
        }

        NodeList errorPageNodes = root.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.ERROR_PAGE));
        for (int i = 0; i < errorPageNodes.getLength(); i++){
            Element errorPageElement = (Element) errorPageNodes.item(i);
            errorPageConfig = new ErrorPageConfig();

            NodeList errorCodeList = errorPageElement.getElementsByTagName(WebTagName.getElementStringTagName(WebTagName.ERROR_CODE));

            if (errorCodeList != null && errorCodeList.getLength() > 0){
                errorPageConfig.setErrorCode(Integer.parseInt(getSingleChild(errorPageElement, WebTagName.getElementStringTagName(WebTagName.ERROR_CODE)).getTextContent().trim()));
            } else {
                errorPageConfig.setExceptionType(getSingleChild(errorPageElement, WebTagName.getElementStringTagName(WebTagName.EXCEPTION_TYPE)).getTextContent().trim());
            }

            errorPageConfig.setLocation(getSingleChild(errorPageElement, WebTagName.getElementStringTagName(WebTagName.LOCATION)).getTextContent().trim());

            errorPageConfigList.add(errorPageConfig);
        }

        webAppConfig.setWelcomeFileList(welcomeFileList);
        webAppConfig.setFilterConfigList(filterConfigList);
        webAppConfig.setServletConfigList(servletConfigList);
        webAppConfig.setListenerClassList(listenerClassList);
        webAppConfig.setErrorPageConfigList(errorPageConfigList);

        return webAppConfig;
    }

    private static Element getSingleChild(Element element, String childName){
        NodeList nlist = element.getElementsByTagName(childName);
        return (Element) nlist.item(0);
    }

}
