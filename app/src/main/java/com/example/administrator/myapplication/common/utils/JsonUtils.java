package com.example.administrator.myapplication.common.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * JSON工具类
 *
 * Created by wxl on 16/6/3.
 */
public class JsonUtils {

    /**
     * 解析json变成Map
     *
     * @param json
     */
    public static Map<String, String> stringToTreeMap(String json) throws Exception {
        JSONObject myJsonObject = new JSONObject(json);
        Map<String, String> treeMap = new TreeMap<String, String>();
        Iterator keys = myJsonObject.keys();
        String key;
        Object value;
        while (keys.hasNext()) {
            key = (String) keys.next();
            value = myJsonObject.get(key);
            treeMap.put(key, String.valueOf(value));
        }

        return treeMap;
    }

    /**
     * 获取post请求签名字符串
     * @param ignoreEmptyString 是否忽略字符串"",是的话，则空串不参与签名，否则参与签名
     * @param treeMap
     * @return
     */
    public static String  getSignString(boolean ignoreEmptyString, Map<String, String> treeMap){
        if(treeMap == null || treeMap.size() == 0){
            //如果是空参数的情况，要默认返回空的json字符串{}
            return "{}";
        }
        StringBuilder sb =  new StringBuilder();
        for (Iterator<Map.Entry<String,String>> iterator = treeMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String,String> obj = iterator.next();
            if(null == obj.getValue()){
                continue;
            }
            if(ignoreEmptyString && "".equals(obj.getValue())) {
                continue;
            }
            sb.append(obj.getKey()).append("=").append(String.valueOf(obj.getValue())).append("&");
        }
        return sb.substring(0, sb.length()-1);
    }
    /**
     * 将json数组转化成集合
     *
     * @param jsonArray
     * @param cls
     * @return
     */
    public static ArrayList getListForJSONARRAY(JSONArray jsonArray, Class cls) {
        ArrayList<Object> list = new ArrayList<Object>();
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                list.add(gson.fromJson(jsonObject.toString(), cls));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return list;
    }

}
