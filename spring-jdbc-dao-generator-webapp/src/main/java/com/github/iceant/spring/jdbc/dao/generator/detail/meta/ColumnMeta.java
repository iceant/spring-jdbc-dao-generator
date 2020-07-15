package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import java.io.Serializable;
import java.util.Objects;

public class ColumnMeta implements Serializable {

    private String catalogName;
    private String schemaName;
    private String tableName;
    private String className;
    private int displaySize;
    private String label;
    private String name;
    private int type;
    private String typeName;
    private int precision;
    private int scale;
    private boolean isAutoIncrement;
    private boolean isCaseSensitive;
    private boolean isCurrency;
    private boolean isDefinitelyWritable;
    private int isNullable;
    private boolean isReadOnly;
    private boolean isSearchable;
    private boolean isSigned;
    private boolean isWritable;

    public ColumnMeta(String catalogName, String schemaName, String tableName, String className, int displaySize, String label, String name, int type, String typeName, int precision, int scale, boolean isAutoIncrement, boolean isCaseSensitive, boolean isCurrency, boolean isDefinitelyWritable, int isNullable, boolean isReadOnly, boolean isSearchable, boolean isSigned, boolean isWritable) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.className = className;
        this.displaySize = displaySize;
        this.label = label;
        this.name = name;
        this.type = type;
        this.typeName = typeName;
        this.precision = precision;
        this.scale = scale;
        this.isAutoIncrement = isAutoIncrement;
        this.isCaseSensitive = isCaseSensitive;
        this.isCurrency = isCurrency;
        this.isDefinitelyWritable = isDefinitelyWritable;
        this.isNullable = isNullable;
        this.isReadOnly = isReadOnly;
        this.isSearchable = isSearchable;
        this.isSigned = isSigned;
        this.isWritable = isWritable;
    }

    public ColumnMeta() {
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }

    public boolean isCurrency() {
        return isCurrency;
    }

    public void setCurrency(boolean currency) {
        isCurrency = currency;
    }

    public boolean isDefinitelyWritable() {
        return isDefinitelyWritable;
    }

    public void setDefinitelyWritable(boolean definitelyWritable) {
        isDefinitelyWritable = definitelyWritable;
    }

    public int getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(int isNullable) {
        this.isNullable = isNullable;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isSearchable() {
        return isSearchable;
    }

    public void setSearchable(boolean searchable) {
        isSearchable = searchable;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public boolean isWritable() {
        return isWritable;
    }

    public void setWritable(boolean writable) {
        isWritable = writable;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Column{" +
                "catalogName='" + catalogName + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", className='" + className + '\'' +
                ", displaySize=" + displaySize +
                ", label='" + label + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", precision=" + precision +
                ", scale=" + scale +
                ", isAutoIncrement=" + isAutoIncrement +
                ", isCaseSensitive=" + isCaseSensitive +
                ", isCurrency=" + isCurrency +
                ", isDefinitelyWritable=" + isDefinitelyWritable +
                ", isNullable=" + isNullable +
                ", isReadOnly=" + isReadOnly +
                ", isSearchable=" + isSearchable +
                ", isSigned=" + isSigned +
                ", isWritable=" + isWritable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnMeta column = (ColumnMeta) o;
        return type == column.type &&
                Objects.equals(tableName, column.tableName) &&
                Objects.equals(name, column.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, name, type);
    }
}
