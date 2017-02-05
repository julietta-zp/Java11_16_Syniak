package service.strategy;

/**
 * Enumeration of tags presented in web.xml file
 */
public enum WebTagName {
    WEB_APP,
    DISPLAY_NAME,

    WELCOME_FILE_LIST,
    WELCOME_FILE,

    FILTER,
    FILTER_NAME,
    FILTER_CLASS,

    INIT_PARAM,
    PARAM_NAME,
    PARAM_VALUE,

    FILTER_MAPPING,

    URL_PATTERN,
    DISPATCHER,

    LISTENER,
    LISTENER_CLASS,

    SERVLET,
    SERVLET_NAME,
    SERVLET_CLASS,
    SERVLET_MAPPING,

    ERROR_PAGE,
    EXCEPTION_TYPE,
    ERROR_CODE,
    LOCATION;

    /**
     * convert enum value to string tag name
     * @param webTagName {@link WebTagName}
     * @return String tag name presented in web.xml
     */
    public static String getElementStringTagName(WebTagName webTagName) {
        return webTagName.name().toLowerCase().replace("_", "-");
    }

    /**
     * convert string tag name to enum value
     * @param tagName String tag name in web.xml
     * @return {@link WebTagName}
     */
    public static WebTagName getElementWebTagName(String tagName){
        return WebTagName.valueOf(tagName.toUpperCase().replace("-", "_"));
    }
}
