package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UniqueKey implements Serializable {
    private String table;
    private String name;
    private List<String> columnNames = new ArrayList<>();

    public UniqueKey() {
    }

    public UniqueKey(String table, String name, String... columnNames) {
        this.table = table;
        this.name = name;
        this.columnNames.addAll(Arrays.asList(columnNames));
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public UniqueKey add(String... columnNames) {
        this.columnNames.addAll(Arrays.asList(columnNames));
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueKey uniqueKey = (UniqueKey) o;
        return Objects.equals(table, uniqueKey.table) &&
                Objects.equals(name, uniqueKey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, name);
    }

    @Override
    public String toString() {
        return "UniqueKey{" +
                "table='" + table + '\'' +
                ", name='" + name + '\'' +
                ", columnNames=" + columnNames +
                '}';
    }
}
