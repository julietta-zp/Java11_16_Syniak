package bean;

import java.util.HashMap;
import java.util.Map;

public class ServletConfig {

    private String servletName;
    private String servletClass;
    private String urlPattern;
    private Map<String, String> servletInitParamMap = new HashMap<>();

    public ServletConfig(){
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Map<String, String> getServletInitParamMap() {
        return servletInitParamMap;
    }

    public void setServletInitParamMap(Map<String, String> servletInitParamMap) {
        this.servletInitParamMap = servletInitParamMap;
    }

    public void addServletInitParamItem(String name, String value){
        this.servletInitParamMap.put(name, value);
    }
}
