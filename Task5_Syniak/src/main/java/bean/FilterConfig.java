package bean;

import java.util.HashMap;
import java.util.Map;

public class FilterConfig {

    private String filterName;

    private String filterClass;

    private Map<String, String> filterInitParamMap = new HashMap<>();

    private Map<String, String> filterUrlDispatcherMapping = new HashMap<>();

    public FilterConfig(){

    }

    public String getFilterName() {
        return filterName;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public Map<String, String> getFilterInitParamMap() {
        return filterInitParamMap;
    }

    public String getFilterInitParam(String parameter){
        return filterInitParamMap.get(parameter);
    }

    public Map<String, String> getFilterUrlDispatcherMapping() {
        return filterUrlDispatcherMapping;
    }

    public void setFilterUrlDispatcherMapping(Map<String, String> filterUrlDispatcherMapping) {
        this.filterUrlDispatcherMapping = filterUrlDispatcherMapping;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public void setFilterInitParamMap(Map<String, String> filterInitParamMap) {
        this.filterInitParamMap = filterInitParamMap;
    }

    public void addFilterInitParamItem(String name, String value){
        this.filterInitParamMap.put(name, value);
    }

    public void addFilterUrlDispatcherMappingItem(String urlPattern, String dispatcher){
        this.filterUrlDispatcherMapping.put(urlPattern, dispatcher);
    }
}
