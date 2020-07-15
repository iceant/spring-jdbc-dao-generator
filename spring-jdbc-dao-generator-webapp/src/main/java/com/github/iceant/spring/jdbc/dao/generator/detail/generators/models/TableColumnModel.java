package com.github.iceant.spring.jdbc.dao.generator.detail.generators.models;

import com.github.iceant.spring.jdbc.dao.generator.detail.meta.ColumnMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;

public class TableColumnModel {
    private ColumnMeta columnMeta;

    public TableColumnModel(ColumnMeta columnMeta) {
        this.columnMeta = columnMeta;
    }

    public ColumnMeta getColumnMeta() {
        return columnMeta;
    }

    public void setColumnMeta(ColumnMeta columnMeta) {
        this.columnMeta = columnMeta;
    }

    public String getJavaFiledName(){
        return GeneratorUtil.tableColumnNameToJavaFiledName(columnMeta.getName());
    }

    public String getJavaFieldType(){
        return GeneratorUtil.tableColumnClassNameToJavaFieldType(columnMeta.getClassName());
    }

    public String getGetMethodName(){
        String javaFieldName = getJavaFiledName();
        return "get"+javaFieldName.substring(0, 1).toUpperCase()+javaFieldName.substring(1);
    }

    public String getSetMethodName(){
        String javaFieldName = getJavaFiledName();
        return "set"+javaFieldName.substring(0, 1).toUpperCase()+javaFieldName.substring(1);
    }

    public String getResultSetGetMethodName(){
        String javaFieldType = getJavaFieldType();
        if(javaFieldType.equalsIgnoreCase("java.lang.Integer")){
            return "getInt";
        }if(javaFieldType.equalsIgnoreCase("java.util.Date")){
            return "getTimestamp";
        }else{
            return "get"+javaFieldType.substring(javaFieldType.lastIndexOf(".")+1);
        }
    }

    public String getTableColumnName(){
        return columnMeta.getName();
    }

    public String getTableColumnNameUpperCase(){
        return getTableColumnName().toUpperCase();
    }
}
