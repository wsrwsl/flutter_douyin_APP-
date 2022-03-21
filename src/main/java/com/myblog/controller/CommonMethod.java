package com.myblog.controller;


import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommonMethod {
    static  String mapToJsonString(Map<String,Object> map){
        JSONObject jsonObj=JSONObject.fromObject(map);
        System.out.println(jsonObj.toString());
        return jsonObj.toString();
    }

    public static Map<String, Object> modelToMap(Object model) {
        if (model == null) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Method method;
        Field[] fields1 = model.getClass().getSuperclass().getDeclaredFields(); // 超类属性
        Field[] fields2 = model.getClass().getDeclaredFields(); // 本类属性
        List<Field> list = new ArrayList<Field>(Arrays.asList(fields1));
        List<Field> list2 = Arrays.asList(fields2);
        list.addAll(list2);
        for (Field field : list) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if(isStatic) {
                continue;	//去除静态成员
            }
            String getMethodName = getMethodName(field.getName());
            try {
                method = model.getClass().getMethod(getMethodName);
                map.put(field.getName(), method.invoke(model));
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private static String getMethodName(String name) {
        if (name != null && name.length() > 2) {
            return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name;
    }


}
