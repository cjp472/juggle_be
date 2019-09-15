package com.juggle.juggle.framework.common.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    public static Map<String, String> toMap(Object obj) throws Throwable {

        Map<String, String> map = new HashMap<>();
        if (null != obj) {
            List<Field> fields = findAllDeclaredFields(obj.getClass());

            for (Field f : fields) {
                Object value = PropertyUtils.getProperty(obj, f.getName());
                if (null != value) {
                    map.put(f.getName(), value.toString());
                }
            }
        }
        return map;
    }

    public static List<Field> findAllDeclaredFields(Class clazz) {
        List<Field> fields = new ArrayList<>();

        if (null != clazz) {
            fields.addAll(CollectionUtils.arrayToList(clazz.getDeclaredFields()));
            fields.addAll(findAllDeclaredFields(clazz.getSuperclass()));
        }
        return fields;
    }

    public static Map<String, Field> findAllDeclaredFieldMap(Class clazz) {
        Map<String, Field> fieldMap = new HashMap<>();

        if (null != clazz) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                fieldMap.put(f.getName(), f);
            }
            fieldMap.putAll(findAllDeclaredFieldMap(clazz.getSuperclass()));
        }
        return fieldMap;
    }

    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

}
