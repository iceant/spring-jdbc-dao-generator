package com.github.iceant.spring.jdbc.dao.generator.detail.util;

import com.github.iceant.spring.jdbc.dao.generator.detail.meta.MetaUtil;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorUtil {

    static final String[] JAVA_KEYWORDS = {
            "abstract","assert","boolean","break","byte","case","catch","char","class","const","continue","default","do","double","else","enum","extends","final","finally","float","for","goto","if","implements","import","instanceof","int","interface","long","native","new","package","private","protected","public","return","strictfp","short","static","super","switch","synchronized","this","throw","throws","transient","try","void","volatile","while"
    };

    static final Map<String, String> CLASS_MAP= new HashMap<>();

    static {
        CLASS_MAP.put(BigInteger.class.getName(), Long.class.getName());
        CLASS_MAP.put(java.sql.Timestamp.class.getName(), java.util.Date.class.getName());
    }

    static boolean isJavaKeyword(String str){
        for(String key : JAVA_KEYWORDS){
            if(key.equals(str)){
                return true;
            }
        }
        return false;
    }

    public static List<String> getTableNames(DataSource dataSource){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            List<String> tableNames = MetaUtil.getTableNames(connection);
            return tableNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String handleDash(String string){
        StringBuilder stringBuilder = new StringBuilder();
        int end = string.length();

        for(int i=0; i<end; i++){
            if(string.charAt(i)=='_'){
                if((i+1)<end) {
                    String upper = String.valueOf(string.charAt(i + 1)).toUpperCase();
                    stringBuilder.append(upper);
                    i= i+1;
                }
            }else{
                stringBuilder.append(string.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    public static String tableColumnNameToJavaFiledName(String tableColumnName){
        String fieldName = handleDash(tableColumnName);
        if(isJavaKeyword(fieldName)){
            return "field"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
        }
        return fieldName;
    }

    public static String tableNameToBeanName(String tableName){
        String beanName = handleDash(tableName);
        return beanName.substring(0,1).toUpperCase()+beanName.substring(1);
    }

    public static String tableColumnClassNameToJavaFieldType(String className) {
        String targetClassName = className;
        if(CLASS_MAP.containsKey(className)){
            return CLASS_MAP.get(className);
        }
        return targetClassName;
    }

    public static void main(String[] args) {
        System.out.println(tableNameToBeanName("abc_def_g"));
        System.out.println(tableNameToBeanName("abc_def_"));
        System.out.println(tableColumnNameToJavaFiledName("class"));
        System.out.println(tableColumnNameToJavaFiledName("is"));
        System.out.println(tableColumnNameToJavaFiledName("if"));

    }

}
