package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import java.io.Serializable;
import java.util.Objects;

public class ReferenceKey implements Serializable {
    private String pkTableCatalog;
    private String pkTableScheme;
    private String pkTableName;
    private String pkColumnName;
    private String fkTableCatalog;
    private String fkTableScheme;
    private String fkTableName;
    private String fkColumnName;
    private int keySequence;
    private String fkName;
    private String pkName;


    public ReferenceKey() {
    }

    public String getPkTableCatalog() {
        return pkTableCatalog;
    }

    public void setPkTableCatalog(String pkTableCatalog) {
        this.pkTableCatalog = pkTableCatalog;
    }

    public String getPkTableScheme() {
        return pkTableScheme;
    }

    public void setPkTableScheme(String pkTableScheme) {
        this.pkTableScheme = pkTableScheme;
    }

    public String getPkTableName() {
        return pkTableName;
    }

    public void setPkTableName(String pkTableName) {
        this.pkTableName = pkTableName;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    public String getFkTableCatalog() {
        return fkTableCatalog;
    }

    public void setFkTableCatalog(String fkTableCatalog) {
        this.fkTableCatalog = fkTableCatalog;
    }

    public String getFkTableScheme() {
        return fkTableScheme;
    }

    public void setFkTableScheme(String fkTableScheme) {
        this.fkTableScheme = fkTableScheme;
    }

    public String getFkTableName() {
        return fkTableName;
    }

    public void setFkTableName(String fkTableName) {
        this.fkTableName = fkTableName;
    }

    public String getFkColumnName() {
        return fkColumnName;
    }

    public void setFkColumnName(String fkColumnName) {
        this.fkColumnName = fkColumnName;
    }

    public int getKeySequence() {
        return keySequence;
    }

    public void setKeySequence(int keySequence) {
        this.keySequence = keySequence;
    }

    public String getFkName() {
        return fkName;
    }

    public void setFkName(String fkName) {
        this.fkName = fkName;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceKey that = (ReferenceKey) o;
        return keySequence == that.keySequence &&
                Objects.equals(pkTableCatalog, that.pkTableCatalog) &&
                Objects.equals(pkTableScheme, that.pkTableScheme) &&
                Objects.equals(pkTableName, that.pkTableName) &&
                Objects.equals(pkColumnName, that.pkColumnName) &&
                Objects.equals(fkTableCatalog, that.fkTableCatalog) &&
                Objects.equals(fkTableScheme, that.fkTableScheme) &&
                Objects.equals(fkTableName, that.fkTableName) &&
                Objects.equals(fkColumnName, that.fkColumnName) &&
                Objects.equals(fkName, that.fkName) &&
                Objects.equals(pkName, that.pkName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkTableCatalog, pkTableScheme, pkTableName, pkColumnName, fkTableCatalog, fkTableScheme, fkTableName, fkColumnName, keySequence, fkName, pkName);
    }

    @Override
    public String toString() {
        return "ReferenceKeys{" +
                "pkTableCatalog='" + pkTableCatalog + '\'' +
                ", pkTableScheme='" + pkTableScheme + '\'' +
                ", pkTableName='" + pkTableName + '\'' +
                ", pkColumnName='" + pkColumnName + '\'' +
                ", fkTableCatalog='" + fkTableCatalog + '\'' +
                ", fkTableScheme='" + fkTableScheme + '\'' +
                ", fkTableName='" + fkTableName + '\'' +
                ", fkColumnName='" + fkColumnName + '\'' +
                ", keySequence=" + keySequence +
                ", fkName='" + fkName + '\'' +
                ", pkName='" + pkName + '\'' +
                '}';
    }
}
