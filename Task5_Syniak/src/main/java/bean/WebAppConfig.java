package bean;

import java.util.List;

/**
 * Web application configurations
 */
public class WebAppConfig {

    private String id;

    private String version;

    private String displayName;

    private List<String> welcomeFileList;

    private List<FilterConfig> filterConfigList;

    private List<String> listenerClassList;

    private List<ServletConfig> servletConfigList;

    private List<ErrorPageConfig> errorPageConfigList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getWelcomeFileList() {
        return welcomeFileList;
    }

    public void setWelcomeFileList(List<String> welcomeFileList) {
        this.welcomeFileList = welcomeFileList;
    }

    public List<FilterConfig> getFilterConfigList() {
        return filterConfigList;
    }

    public void setFilterConfigList(List<FilterConfig> filterConfigList) {
        this.filterConfigList = filterConfigList;
    }

    public List<String> getListenerClassList() {
        return listenerClassList;
    }

    public void setListenerClassList(List<String> listenerClassList) {
        this.listenerClassList = listenerClassList;
    }

    public List<ServletConfig> getServletConfigList() {
        return servletConfigList;
    }

    public void setServletConfigList(List<ServletConfig> servletConfigList) {
        this.servletConfigList = servletConfigList;
    }

    public List<ErrorPageConfig> getErrorPageConfigList() {
        return errorPageConfigList;
    }

    public void setErrorPageConfigList(List<ErrorPageConfig> errorPageConfigList) {
        this.errorPageConfigList = errorPageConfigList;
    }
}
