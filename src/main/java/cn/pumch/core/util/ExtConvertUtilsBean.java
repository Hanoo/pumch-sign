package cn.pumch.core.util;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

import java.lang.reflect.Array;

public class ExtConvertUtilsBean extends ConvertUtilsBean {

    @Override
    public String convert(Object value) {
        if (value == null) {
            return null;
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) < 1) {
                return (null);
            }
            value = Array.get(value, 0);
            if (value == null) {
                return null;
            } else {
                Converter converter = lookup(String.class);
                return (converter.convert(String.class, value)).toString();
            }
        } else {
            Converter converter = null;
            if(value instanceof java.util.Date ){
                converter = lookup(java.util.Date.class);
            }else{
                converter = lookup(String.class);
            }
            return (converter.convert(String.class, value)).toString();
        }
    }
}
