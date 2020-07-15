package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import java.io.Serializable;
import java.util.Objects;

public class IndexInfo implements Serializable {
    private String tableCatalog;
    private String tableScheme;
    private String tableName;
    private boolean nonUnique;
    private String indexQualifier;
    private String indexName;
    private int type;
    private int ordinalPosition;
    private String columnName;
    private String ascOrDesc;
    private long cardinality;
    private long pages;
    private String filterCondition;

    public IndexInfo() {
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableScheme() {
        return tableScheme;
    }

    public void setTableScheme(String tableScheme) {
        this.tableScheme = tableScheme;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    public String getIndexQualifier() {
        return indexQualifier;
    }

    public void setIndexQualifier(String indexQualifier) {
        this.indexQualifier = indexQualifier;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    public long getCardinality() {
        return cardinality;
    }

    public void setCardinality(long cardinality) {
        this.cardinality = cardinality;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexInfo indexInfo = (IndexInfo) o;
        return nonUnique == indexInfo.nonUnique &&
                type == indexInfo.type &&
                ordinalPosition == indexInfo.ordinalPosition &&
                cardinality == indexInfo.cardinality &&
                pages == indexInfo.pages &&
                Objects.equals(tableCatalog, indexInfo.tableCatalog) &&
                Objects.equals(tableScheme, indexInfo.tableScheme) &&
                Objects.equals(tableName, indexInfo.tableName) &&
                Objects.equals(indexQualifier, indexInfo.indexQualifier) &&
                Objects.equals(indexName, indexInfo.indexName) &&
                Objects.equals(columnName, indexInfo.columnName) &&
                Objects.equals(ascOrDesc, indexInfo.ascOrDesc) &&
                Objects.equals(filterCondition, indexInfo.filterCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableCatalog, tableScheme, tableName, nonUnique, indexQualifier, indexName, type, ordinalPosition, columnName, ascOrDesc, cardinality, pages, filterCondition);
    }

    @Override
    public String toString() {
        return "IndexInfo{" +
                "tableCatalog='" + tableCatalog + '\'' +
                ", tableScheme='" + tableScheme + '\'' +
                ", tableName='" + tableName + '\'' +
                ", nonUnique=" + nonUnique +
                ", indexQualifier='" + indexQualifier + '\'' +
                ", indexName='" + indexName + '\'' +
                ", type=" + type +
                ", ordinalPosition=" + ordinalPosition +
                ", columnName='" + columnName + '\'' +
                ", ascOrDesc='" + ascOrDesc + '\'' +
                ", cardinality=" + cardinality +
                ", pages=" + pages +
                ", filterCondition='" + filterCondition + '\'' +
                '}';
    }
}
