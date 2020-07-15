package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrimaryKey implements Serializable {
    private String table;
    private String name;
    private int keySequence;
    private List<String> columnNames = new ArrayList<>();

    public PrimaryKey() {
    }

    public PrimaryKey(String table, String name, int keySeq, String... columns) {
        this.table = table;
        this.name = name;
        this.keySequence = keySeq;
        columnNames.addAll(Arrays.asList(columns));
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public PrimaryKey add(String... columnNames) {
        this.columnNames.addAll(Arrays.asList(columnNames));
        return this;
    }

    public PrimaryKey add(int index, String columnName) {
        columnNames.add(index, columnName);
        return this;
    }

    public int getKeySequence() {
        return keySequence;
    }

    public void setKeySequence(int keySequence) {
        this.keySequence = keySequence;
    }

    @Override
    public String toString() {
        return "PrimaryKey{" +
                "table='" + table + '\'' +
                ", name='" + name + '\'' +
                ", keySequence=" + keySequence +
                ", columnNames=" + columnNames +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimaryKey that = (PrimaryKey) o;
        return Objects.equals(table, that.table) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, name);
    }
}
