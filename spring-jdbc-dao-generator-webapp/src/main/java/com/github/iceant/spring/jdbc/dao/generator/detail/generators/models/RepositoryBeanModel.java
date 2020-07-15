package com.github.iceant.spring.jdbc.dao.generator.detail.generators.models;


import com.github.iceant.spring.jdbc.dao.generator.detail.meta.ColumnMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositoryBeanModel {
    private TableMeta tableMeta;
    private String tableBeanPackageName;
    private String tableBeanClassName;
    private String packageName;
    private String className;
    private List<TableColumnModel> columns = new ArrayList<>();

    public RepositoryBeanModel(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
        for(ColumnMeta columnMeta : tableMeta.getColumns()){
            columns.add(new TableColumnModel(columnMeta));
        }
    }

    public String getTableBeanPackageName() {
        return tableBeanPackageName;
    }

    public void setTableBeanPackageName(String tableBeanPackageName) {
        this.tableBeanPackageName = tableBeanPackageName;
    }

    public TableMeta getTableMeta() {
        return tableMeta;
    }

    public void setTableMeta(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
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

    public String getTableBeanClassName() {
        return tableBeanClassName;
    }

    public void setTableBeanClassName(String tableBeanClassName) {
        this.tableBeanClassName = tableBeanClassName;
    }

    public String getTableBeanInstanceName(){
        return tableBeanClassName.toLowerCase();
    }

    public String getSelectSql(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        for(ColumnMeta cm : tableMeta.getColumns()){
            stringBuilder.append(cm.getName()).append(",");
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
        stringBuilder.append(" FROM ").append(tableMeta.getName());
        return stringBuilder.toString();
    }

    public String getInsertSql(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder paramBlock = new StringBuilder();

        stringBuilder.append("INSERT INTO ").append(tableMeta.getName());
        stringBuilder.append("(");

        for(ColumnMeta cm : tableMeta.getColumns()){
            stringBuilder.append(cm.getName()).append(",");
            paramBlock.append("?").append(",");
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());//删除 "," 符号
        paramBlock.delete(paramBlock.length()-1, paramBlock.length());
        stringBuilder.append(") VALUES (").append(paramBlock);

        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String getInsertParams(){
        StringBuilder stringBuilder = new StringBuilder();
        for(TableColumnModel tcm : columns){
            stringBuilder.append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()").append(",");
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
        return stringBuilder.toString();
    }

    public String getInsertPreparedStatementSetter(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("new PreparedStatementSetter(){\n");
        stringBuffer.append("\t\t\tpublic void setValues(PreparedStatement preparedStatement) throws SQLException {\n");
        int index = 1;
        for(TableColumnModel tcm : columns){
//            stringBuffer.append("\t\t\t\tif(").append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()==null){\n")
//                    .append("\t\t\t\t\tpreparedStatement.setNull(")
//                        .append(index)
//                        .append(", Types.NULL")
////                    .append(tcm.getColumnMeta().getType())
//                    .append(");\n")
//                    .append("\t\t\t\t}else{\n");
//            stringBuffer.append("\t\t\t\t\tpreparedStatement.setObject(")
//                    .append(index).append(",")
//                    .append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("(),")
//                    .append(tcm.getColumnMeta().getType())
//                    .append(");\n");
//            stringBuffer.append("\t\t\t\t}/*end else*/\n");
//            StatementCreatorUtils.setParameterValue(ps, parameterPosition, argType, argValue);
            stringBuffer.append("\t\t\t\t")
                    .append("StatementCreatorUtils.setParameterValue(preparedStatement, ")
                    .append(index).append(", ")
                    .append(tcm.getColumnMeta().getType()).append(", ")
                    .append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()").append(");\n");
            index = index+1;
        }
        stringBuffer.append("\t\t\t}\n");
        stringBuffer.append("\t\t}");
        return stringBuffer.toString();
    }


    public List<TableColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumnModel> columns) {
        this.columns = columns;
    }

    public String getUpdateSql(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder paramBlock = new StringBuilder();
        stringBuilder.append("UPDATE ").append(tableMeta.getName());
        stringBuilder.append(" SET ");
        for(TableColumnModel tcm : columns) {
            stringBuilder.append(tcm.getTableColumnName()).append("=?,");
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
        stringBuilder.append(" WHERE ");
        if(tableMeta.hasPrimaryKey()){
            List<String> columnNames = tableMeta.getPrimaryKey().getColumnNames();
            for(int i=0; i<columnNames.size(); i++){
                String pk = columnNames.get(i);
                if(i>0){
                    stringBuilder.append(" AND ").append(pk).append("=?");
                }else{
                    stringBuilder.append(pk).append("=?");
                }
            }
        }

        return stringBuilder.toString();
    }

    public String getUpdateParams(){
        StringBuilder stringBuilder = new StringBuilder();
        for(TableColumnModel tcm : columns){
            stringBuilder.append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()").append(",");
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
        if(tableMeta.hasPrimaryKey()){
            List<String> columnNames = tableMeta.getPrimaryKey().getColumnNames();
            for(int i=0; i<columnNames.size(); i++){
                String pk = columnNames.get(i);
                TableColumnModel tcm = new TableColumnModel(tableMeta.getColumnMeta(pk));
                stringBuilder.append(",").append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()");
            }
        }
        return stringBuilder.toString();
    }

    public String getUpdatePreparedStatementSetter(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("new PreparedStatementSetter(){\n");
        stringBuffer.append("\t\t\tpublic void setValues(PreparedStatement preparedStatement) throws SQLException {\n");
        int index = 1;
        for(TableColumnModel tcm : columns){
//            stringBuffer.append("\t\t\t\tif(").append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()==null){\n")
//                    .append("\t\t\t\t\tpreparedStatement.setNull(")
//                        .append(index).append(", ")
//                        .append("Types.NULL")
////                        .append(tcm.getColumnMeta().getType())
//                    .append(");\n")
//                    .append("\t\t\t\t}else{\n");
//            stringBuffer.append("\t\t\t\t\tpreparedStatement.setObject(")
//                    .append(index).append(",")
//                    .append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("(),")
//                    .append(tcm.getColumnMeta().getType())
//                    .append(");\n");
//            stringBuffer.append("\t\t\t\t}/*end else*/\n");

            stringBuffer.append("\t\t\t\t")
                    .append("StatementCreatorUtils.setParameterValue(preparedStatement, ")
                    .append(index).append(", ")
                    .append(tcm.getColumnMeta().getType()).append(", ")
                    .append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()").append(");\n");

            index = index+1;
        }
        if(tableMeta.hasPrimaryKey()){
            List<String> columnNames = tableMeta.getPrimaryKey().getColumnNames();
            for(int i=0; i<columnNames.size(); i++){
                String pk = columnNames.get(i);
                TableColumnModel tcm = new TableColumnModel(tableMeta.getColumnMeta(pk));
//                stringBuffer.append("\t\t\t\tpreparedStatement.setObject(")
//                        .append(index).append(",")
//                        .append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("(),")
//                        .append(tcm.getColumnMeta().getType())
//                        .append(");\n");
                stringBuffer.append("\t\t\t\t")
                        .append("StatementCreatorUtils.setParameterValue(preparedStatement, ")
                        .append(index).append(", ")
                        .append(tcm.getColumnMeta().getType()).append(", ")
                        .append(getTableBeanInstanceName()).append(".").append(tcm.getGetMethodName()).append("()").append(");\n");
                index = index+1;
            }
        }
        stringBuffer.append("\t\t\t}\n");
        stringBuffer.append("\t\t}");
        return stringBuffer.toString();
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

    public String getFindByPkMethodParams(){
        StringBuilder param = new StringBuilder();
        if(tableMeta.hasPrimaryKey()){
            List<String> columnNames = tableMeta.getPrimaryKey().getColumnNames();
            for(int i=0; i<columnNames.size(); i++){
                String pk = columnNames.get(i);
                TableColumnModel tcm = new TableColumnModel(tableMeta.getColumnMeta(pk));
                param.append(tcm.getJavaFieldType()).append(" ").append(tcm.getJavaFiledName()).append(", ");
            }
            param.delete(param.length()-2, param.length());
        }
        return param.toString();
    }

    public String getFindByPkQueryParams(){
        StringBuilder param = new StringBuilder();
        if(tableMeta.hasPrimaryKey()){
            List<String> columnNames = tableMeta.getPrimaryKey().getColumnNames();
            for(int i=0; i<columnNames.size(); i++){
                String pk = columnNames.get(i);
                TableColumnModel tcm = new TableColumnModel(tableMeta.getColumnMeta(pk));
                param.append(tcm.getJavaFiledName()).append(", ");
            }
            param.delete(param.length()-2, param.length());
        }
        return param.toString();
    }

    public String getFindByPkSQL(){
        StringBuilder param = new StringBuilder();
        param.append(getSelectSql());
        if(tableMeta.hasPrimaryKey()){
            param.append(" WHERE 1=1 ");
            List<String> columnNames = tableMeta.getPrimaryKey().getColumnNames();
            for(int i=0; i<columnNames.size(); i++){
                String pk = columnNames.get(i);
                TableColumnModel tcm = new TableColumnModel(tableMeta.getColumnMeta(pk));
                param.append(" AND ").append(tcm.getTableColumnName()).append("=?");
            }
        }
        return param.toString();
    }

    public String getDeleteSql(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ").append(tableMeta.getName());
        List<TableColumnModel> tableColumnModels = getPrimaryKeyColumns();
        if(tableColumnModels.size()==0){
            tableColumnModels = columns;
        }

        stringBuilder.append(" WHERE ");
        for(int i=0; i< tableColumnModels.size(); i++){
            TableColumnModel tableColumnModel = tableColumnModels.get(i);
            if(i==0) {
                stringBuilder.append(tableColumnModel.getTableColumnName()).append("=?");
            }else{
                stringBuilder.append(" AND ").append(tableColumnModel.getTableColumnName()).append("=?");
            }
        }
        return stringBuilder.toString();
    }

    public String getDeleteParams(){
        StringBuilder stringBuilder = new StringBuilder();
        List<TableColumnModel> tableColumnModels = getPrimaryKeyColumns();
        if(tableColumnModels.size()==0){
            tableColumnModels = columns;
        }

        for(int i=0; i< tableColumnModels.size(); i++){
            TableColumnModel tableColumnModel = tableColumnModels.get(i);
            if(i==0) {
                stringBuilder.append(getTableBeanInstanceName()).append(".").append(tableColumnModel.getGetMethodName()).append("()");
            }else{
                stringBuilder.append(",").append(getTableBeanInstanceName()).append(".").append(tableColumnModel.getGetMethodName()).append("()");
            }
        }
        return stringBuilder.toString();
    }

    public String getTableName(){
        return tableMeta.getName();
    }
}
