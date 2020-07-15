package com.github.iceant.spring.jdbc.dao.generator.detail.meta;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetaUtil {

    public static String printResultSet(ResultSet rs, String title) {
        StringBuffer sb = new StringBuffer();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String titles = String.format("Resultset(%s) : COLUMNS - %d\n", title, columnCount);
            sb.append(titles);
            StringBuilder lineBuilder = new StringBuilder();
            for (int i = 0; i < titles.length(); i++) {
                lineBuilder.append("-");
            }
            lineBuilder.append("\n");
            String line = lineBuilder.toString();
            for (int i = 1; i <= columnCount; i++) {
                String catalogName = rsmd.getCatalogName(i);
                String columnTableName = rsmd.getTableName(i);
                String columnClassName = rsmd.getColumnClassName(i);
                int columnDisplaySize = rsmd.getColumnDisplaySize(i);
                String columnLabel = rsmd.getColumnLabel(i);
                String columnName = rsmd.getColumnName(i);
                int columnType = rsmd.getColumnType(i);
                String columnTypeName = rsmd.getColumnTypeName(i);
                int precision = rsmd.getPrecision(i);
                int scale = rsmd.getScale(i);
                String schemaName = rsmd.getSchemaName(i);
                boolean isAutoIncrement = rsmd.isAutoIncrement(i);
                boolean isCaseSensitive = rsmd.isCaseSensitive(i);
                boolean isCurrency = rsmd.isCurrency(i);
                boolean isDefinitelyWritable = rsmd.isDefinitelyWritable(i);
                int isNullable = rsmd.isNullable(i);
                boolean isReadOnly = rsmd.isReadOnly(i);
                boolean isSearchable = rsmd.isSearchable(i);
                boolean isSigned = rsmd.isSigned(i);
                boolean isWritable = rsmd.isWritable(i);

                sb.append(line);
                sb.append(String.format("  %s: %d\n", "Catalog Index", i));
                sb.append(String.format("  %s: %s\n", "Catalog Name", catalogName));
                sb.append(String.format("  %s: %s\n", "Schema Name", schemaName));
                sb.append(String.format("  %s: %s\n", "Column Table Name", columnTableName));
                sb.append(String.format("  %s: %s\n", "Column Class Name", columnClassName));
                sb.append(String.format("  %s: %d\n", "Column Display Size", columnDisplaySize));
                sb.append(String.format("  %s: %s\n", "Column Label", columnLabel));
                sb.append(String.format("  %s: %s\n", "Column Name", columnName));
                sb.append(String.format("  %s: %d\n", "Column Type", columnType));
                sb.append(String.format("  %s: %s\n", "Column Type Name", columnTypeName));
                sb.append(String.format("  %s: %d\n", "Precision", precision));
                sb.append(String.format("  %s: %d\n", "Scale", scale));
                sb.append(String.format("  %s: %s\n", "isAutoIncrement", isAutoIncrement ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isCaseSensitive", isCaseSensitive ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isCurrency", isCurrency ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isDefinitelyWritable", isDefinitelyWritable ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %d\n", "isNullable", isNullable));
                sb.append(String.format("  %s: %s\n", "isReadOnly", isReadOnly ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isSearchable", isSearchable ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isSearchable", isSearchable ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isSigned", isSigned ? "TRUE" : "FALSE"));
                sb.append(String.format("  %s: %s\n", "isWritable", isWritable ? "TRUE" : "FALSE"));
            }

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
        return sb.toString();
    }

    public static List<String> getTableNames(Connection conn) {
        List<String> result = new ArrayList<>();
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(conn.getCatalog(), conn.getSchema(), null, new String[]{"TABLE"});
            while (rs.next()) {
                result.add(rs.getString("TABLE_NAME"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static List<ColumnMeta> getTableColumns(Connection connection, String tableName) {
        List<ColumnMeta> columns = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));
            ResultSetMetaData rsmd = stmt.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String catalogName = rsmd.getCatalogName(i);
                String columnTableName = rsmd.getTableName(i);
                String columnClassName = rsmd.getColumnClassName(i);
                int columnDisplaySize = rsmd.getColumnDisplaySize(i);
                String columnLabel = rsmd.getColumnLabel(i);
                String columnName = rsmd.getColumnName(i);
                int columnType = rsmd.getColumnType(i);
                String columnTypeName = rsmd.getColumnTypeName(i);

                int precision = rsmd.getPrecision(i);
                int scale = rsmd.getScale(i);
                String schemaName = rsmd.getSchemaName(i);
                boolean isAutoIncrement = rsmd.isAutoIncrement(i);
                boolean isCaseSensitive = rsmd.isCaseSensitive(i);
                boolean isCurrency = rsmd.isCurrency(i);
                boolean isDefinitelyWritable = rsmd.isDefinitelyWritable(i);
                int isNullable = rsmd.isNullable(i);
                boolean isReadOnly = rsmd.isReadOnly(i);
                boolean isSearchable = rsmd.isSearchable(i);
                boolean isSigned = rsmd.isSigned(i);
                boolean isWritable = rsmd.isWritable(i);

                columns.add(new ColumnMeta(catalogName, schemaName, columnTableName, columnClassName, columnDisplaySize, columnLabel, columnName, columnType, columnTypeName, precision, scale, isAutoIncrement, isCaseSensitive, isCurrency, isDefinitelyWritable, isNullable, isReadOnly, isSearchable, isSigned, isWritable));
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columns;
    }


    public static TableMeta getTableMeta(DataSource dataSource, String tableName) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            TableMeta tm = new TableMeta(conn.getCatalog(), conn.getSchema(), tableName);
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet primaryKeyRs = dmd.getPrimaryKeys(conn.getCatalog(), conn.getSchema(), tableName);
            while (primaryKeyRs.next()) {
                String primaryKeyName = primaryKeyRs.getString("PK_NAME");
                String columnName = primaryKeyRs.getString("COLUMN_NAME");
                int keySeq = primaryKeyRs.getInt("KEY_SEQ");
                PrimaryKey primaryKey = new PrimaryKey();
                primaryKey.setName(primaryKeyName);
                primaryKey.setTable(tableName);
                primaryKey.add(columnName);
                primaryKey.setKeySequence(keySeq);
                tm.setPrimaryKey(primaryKey);
            }
            primaryKeyRs.close();


            ResultSet exportedKeys = dmd.getExportedKeys(conn.getCatalog(), conn.getSchema(), tableName);
            while (exportedKeys.next()) {
                ReferenceKey rk = new ReferenceKey();
                rk.setPkTableCatalog(exportedKeys.getString("PKTABLE_CAT"));
                rk.setPkTableScheme(exportedKeys.getString("PKTABLE_SCHEM"));
                rk.setPkTableName(exportedKeys.getString("PKTABLE_NAME"));
                rk.setPkColumnName(exportedKeys.getString("PKCOLUMN_NAME"));
                rk.setFkTableCatalog(exportedKeys.getString("FKTABLE_CAT"));
                rk.setFkTableScheme(exportedKeys.getString("FKTABLE_SCHEM"));
                rk.setFkTableName(exportedKeys.getString("FKTABLE_NAME"));
                rk.setFkColumnName(exportedKeys.getString("FKCOLUMN_NAME"));
                rk.setKeySequence(exportedKeys.getInt("KEY_SEQ"));
                rk.setFkName(exportedKeys.getString("FK_NAME"));
                rk.setPkName(exportedKeys.getString("PK_NAME"));

                tm.addExportedKey(rk);
            }
            exportedKeys.close();

            ResultSet importedKeys = dmd.getImportedKeys(conn.getCatalog(), conn.getSchema(), tableName);
            while (importedKeys.next()) {
                ReferenceKey rk = new ReferenceKey();
                rk.setPkTableCatalog(importedKeys.getString("PKTABLE_CAT"));
                rk.setPkTableScheme(importedKeys.getString("PKTABLE_SCHEM"));
                rk.setPkTableName(importedKeys.getString("PKTABLE_NAME"));
                rk.setPkColumnName(importedKeys.getString("PKCOLUMN_NAME"));
                rk.setFkTableCatalog(importedKeys.getString("FKTABLE_CAT"));
                rk.setFkTableScheme(importedKeys.getString("FKTABLE_SCHEM"));
                rk.setFkTableName(importedKeys.getString("FKTABLE_NAME"));
                rk.setFkColumnName(importedKeys.getString("FKCOLUMN_NAME"));
                rk.setKeySequence(importedKeys.getInt("KEY_SEQ"));
                rk.setFkName(importedKeys.getString("FK_NAME"));
                rk.setPkName(importedKeys.getString("PK_NAME"));
                tm.addImportedKey(rk);
            }
            importedKeys.close();

            ResultSet indexInfoRs = dmd.getIndexInfo(conn.getCatalog(), conn.getSchema(), tableName, false, false);
            while (indexInfoRs.next()) {
                IndexInfo indexInfo = new IndexInfo();
                indexInfo.setTableCatalog(indexInfoRs.getString("TABLE_CAT"));
                indexInfo.setTableScheme(indexInfoRs.getString("TABLE_SCHEM"));
                indexInfo.setTableName(indexInfoRs.getString("TABLE_NAME"));
                indexInfo.setNonUnique(indexInfoRs.getBoolean("NON_UNIQUE"));
                indexInfo.setIndexQualifier(indexInfoRs.getString("INDEX_QUALIFIER"));
                indexInfo.setIndexName(indexInfoRs.getString("INDEX_NAME"));
                indexInfo.setOrdinalPosition(indexInfoRs.getInt("ORDINAL_POSITION"));
                indexInfo.setColumnName(indexInfoRs.getString("COLUMN_NAME"));
                indexInfo.setAscOrDesc(indexInfoRs.getString("ASC_OR_DESC"));
                indexInfo.setCardinality(indexInfoRs.getLong("CARDINALITY"));
                indexInfo.setPages(indexInfoRs.getLong("PAGES"));
                indexInfo.setFilterCondition(indexInfoRs.getString("FILTER_CONDITION"));
                tm.addIndexInfo(indexInfo);
            }
            indexInfoRs.close();

            tm.setColumns(getTableColumns(conn, tableName));
            return tm;
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
