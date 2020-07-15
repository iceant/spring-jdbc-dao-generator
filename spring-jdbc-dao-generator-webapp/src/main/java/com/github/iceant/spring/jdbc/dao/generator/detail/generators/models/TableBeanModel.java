package com.github.iceant.spring.jdbc.dao.generator.detail.generators.models;

import com.github.iceant.spring.jdbc.dao.generator.detail.meta.ColumnMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableBeanModel {
    private TableMeta tableMeta;
    private String packageName;
    private String className;
    private List<TableColumnModel> columns = new ArrayList<>();

    public TableBeanModel(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
        for(ColumnMeta columnMeta:tableMeta.getColumns()){
            columns.add(new TableColumnModel(columnMeta));
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public TableMeta getTableMeta() {
        return tableMeta;
    }

    public void setTableMeta(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    public List<TableColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumnModel> columns) {
        this.columns = columns;
    }

    public String getTableName(){
        return tableMeta.getName();
    }

    public boolean getHasPrimaryKey(){
        return tableMeta.hasPrimaryKey();
    }

    public List<TableColumnModel> getPrimaryKeyColumns(){
        if(!tableMeta.hasPrimaryKey()){
            return Collections.EMPTY_LIST;
        }

        List<TableColumnModel> tableColumnModels = new ArrayList<>();
        for(String pk : tableMeta.getPrimaryKey().getColumnNames()){
            tableColumnModels.add(new TableColumnModel(tableMeta.getColumnMeta(pk)));
        }
        return tableColumnModels;
    }

    public String getEqualsCode(){
        StringBuffer sb = new StringBuffer();
        sb.append("").append("if (this == o) return true;").append("\n");
        sb.append("\t\t").append("if (o == null || getClass() != o.getClass()) return false;").append("\n");
        sb.append("\t\t").append(getClassName()).append(" that = (").append(getClassName()).append(")o;").append("\n");
        sb.append("\t\t").append("return ");
        List<TableColumnModel> columnModels;
        if(getHasPrimaryKey()) {
            columnModels = getPrimaryKeyColumns();
        }else{
            columnModels = getColumns();
        }

        for(int i = 0, size = columnModels.size(); i<size; i++){
            TableColumnModel columnModel = columnModels.get(i);
            sb.append("Objects.equals(").append(columnModel.getJavaFiledName()).append(", that.").append(columnModel.getJavaFiledName()).append(")");
            if(i<size-1){
                sb.append(" && \n").append("\t\t\t");
            }
        }
        sb.append(";");
        return sb.toString();
    }

    public String getHashCode(){
        StringBuffer sb = new StringBuffer();
        sb.append("return ");

        List<TableColumnModel> columnModels;
        if(getHasPrimaryKey()) {
            columnModels = getPrimaryKeyColumns();
        }else{
            columnModels = getColumns();
        }

        sb.append("Objects.hash(");
        for(int i = 0, size = columnModels.size(); i<size; i++){
            TableColumnModel columnModel = columnModels.get(i);
            sb.append(columnModel.getJavaFiledName());
            if(i<size-1){
                sb.append(", ");
            }
        }
        sb.append(");");
        return sb.toString();
    }
}
