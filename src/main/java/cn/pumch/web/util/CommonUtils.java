package cn.pumch.web.util;

import cn.pumch.core.util.JsonDateValueProcessor;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import java.util.List;
import java.util.UUID;

/**
 * JSON格式化工具类
 */
public class CommonUtils {
    private static final JsonConfig jsonConfig = new JsonConfig();

    public static JsonConfig getJsonConfig() {
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm"));
        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object object, String fieldName, Object fieldValue) {
                if(fieldValue instanceof List){
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) fieldValue;
                    if (list.size()==0) {
                        return true;
                    }
                }
                //过滤条件：值为null时过滤
                return null == fieldValue;
            }
        };
        jsonConfig.setJsonPropertyFilter(filter);
        return jsonConfig;
    }

    public static String getUUId() {
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-","");
    }
}
