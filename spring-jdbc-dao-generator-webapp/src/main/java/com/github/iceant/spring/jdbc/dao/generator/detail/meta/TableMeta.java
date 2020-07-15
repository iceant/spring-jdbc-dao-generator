package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TableMeta implements Serializable {
    private String tableCatalog;
    private String tableScheme;
    private String name;
    private List<ColumnMeta> columns = new LinkedList<>();
    private PrimaryKey primaryKey;
    private List<UniqueKey> uniqueKeys = new LinkedList<>();
    private List<ReferenceKey> importedKeys = new LinkedList<>();
    private List<ReferenceKey> exportedKeys = new LinkedList<>();
    private List<IndexInfo> indexInfos = new LinkedList<>();

    public TableMeta() {
    }

    public TableMeta(String tableCatalog, String tableScheme, String name) {
        this.tableCatalog = tableCatalog;
        this.tableScheme = tableScheme;
        this.name = name;
    }

    public String getTableScheme() {
        return tableScheme;
    }

    public void setTableScheme(String tableScheme) {
        this.tableScheme = tableScheme;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnMeta> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMeta> columns) {
        this.columns = columns;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        if (this.primaryKey == null) {
            this.primaryKey = primaryKey;
        } else {
            int startIdx = primaryKey.getKeySequence() - 1;
            for (String columnName : primaryKey.getColumnNames()) {
                this.primaryKey.add(startIdx, columnName);
                startIdx++;
            }
        }
    }

    public List<UniqueKey> getUniqueKeys() {
        return uniqueKeys;
    }

    public void setUniqueKeys(List<UniqueKey> uniqueKeys) {
        this.uniqueKeys = uniqueKeys;
    }

    public List<ReferenceKey> getImportedKeys() {
        return importedKeys;
    }

    public void setImportedKeys(List<ReferenceKey> importedKeys) {
        this.importedKeys = importedKeys;
    }

    public ColumnMeta getColumnMeta(String columnName) {
        for (ColumnMeta columnMeta : columns) {
            if (columnMeta.getName().equalsIgnoreCase(columnName)) {
                return columnMeta;
            }
        }
        return null;
    }

    public List<ReferenceKey> getExportedKeys() {
        return exportedKeys;
    }

    public void setExportedKeys(List<ReferenceKey> exportedKeys) {
        this.exportedKeys = exportedKeys;
    }

    public List<IndexInfo> getIndexInfos() {
        return indexInfos;
    }

    public void setIndexInfos(List<IndexInfo> indexInfos) {
        this.indexInfos = indexInfos;
    }


    public TableMeta addExportedKey(ReferenceKey... referenceKeys) {
        for (ReferenceKey key : referenceKeys) {
            if (!exportedKeys.contains(key)) {
                exportedKeys.add(key.getKeySequence() - 1, key);
            }
        }
        return this;
    }

    public TableMeta addImportedKey(ReferenceKey... keys) {
        for (ReferenceKey key : keys) {
            if (!importedKeys.contains(key)) {
                importedKeys.add(key.getKeySequence() - 1, key);
            }
        }
        return this;
    }

    public TableMeta addIndexInfo(IndexInfo... iis) {
        for (IndexInfo indexInfo : iis) {
            if (!indexInfos.contains(indexInfo)) {
                indexInfos.add(indexInfo);
            }
        }
        return this;
    }


    @Override
    public String toString() {
        return "TableMeta{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", primaryKey=" + primaryKey +
                ", uniqueKeys=" + uniqueKeys +
                ", importedKeys=" + importedKeys +
                ", exportedKeys=" + exportedKeys +
                ", indexInfos=" + indexInfos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableMeta tableMeta = (TableMeta) o;
        return Objects.equals(tableCatalog, tableMeta.tableCatalog) &&
                Objects.equals(tableScheme, tableMeta.tableScheme) &&
                Objects.equals(name, tableMeta.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableCatalog, tableScheme, name);
    }

    public String prettyPrint() {
        StringBuffer sb = new StringBuffer();
        String title = String.format("Table - %s\n", name);
        sb.append(title);
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < title.length(); i++) {
            lineBuilder.append("-");
        }
        lineBuilder.append("\n");
        String line = lineBuilder.toString();
        sb.append(line);

        for (ColumnMeta cm : columns) {
            sb.append(String.format("  [CL] %s %s(%d) %s -> %s\n", cm.getName(), cm.getTypeName(), cm.getPrecision(), cm.isAutoIncrement() ? "AUTO INCREMENT" : "", cm.getClassName()));
        }

        PrimaryKey pk = getPrimaryKey();
        sb.append(String.format("  [PK] %s(%s)\n", pk.getName(), pk.getColumnNames()));

        for (ReferenceKey key : importedKeys) {
            sb.append(String.format("  [FK] %s(%s) -> %s(%s)\n", key.getFkName(), key.getFkColumnName(), key.getPkTableName(), key.getPkColumnName()));
        }

        return sb.toString();
    }

    public boolean hasPrimaryKey() {
        return primaryKey != null;
    }

    public boolean hasImportedKey() {
        return importedKeys != null && importedKeys.size() > 0;
    }

    public boolean hasImportedKey(String name) {
        if (!hasImportedKey()) return false;
        for (ReferenceKey rk : importedKeys) {
            if (rk.getFkColumnName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPrimaryKey(String name) {
        if (!hasPrimaryKey()) return false;
        for (String pk : primaryKey.getColumnNames()) {
            if (pk.equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
