package {{packageName}};

import {{tableBeanPackageName}}.{{tableBeanClassName}};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class {{className}} {
    public static final String SELECT_SQL = "{{selectSql}}";

    public static final String TABLE = "{{tableName}}";
    {{#columns}}
    public static final String {{tableColumnNameUpperCase}}="{{tableColumnName}}";
    {{/columns}}

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public {{className}}(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }


    public static final RowMapper<{{tableBeanClassName}}> ROW_MAPPER = new RowMapper<{{tableBeanClassName}}>() {
        public {{tableBeanClassName}} mapRow(java.sql.ResultSet rs, int i) throws java.sql.SQLException {
            {{tableBeanClassName}} bean = new {{tableBeanClassName}}();
            {{#columns}}
            bean.{{setMethodName}}(rs.{{resultSetGetMethodName}}("{{tableColumnName}}"));
            {{/columns}}
            return bean;
        }
    };

    public static final ResultSetExtractor<{{tableBeanClassName}}> RESULT_SET_EXTRACTOR = new ResultSetExtractor<{{tableBeanClassName}}>() {
        public {{tableBeanClassName}} extractData(java.sql.ResultSet rs) throws java.sql.SQLException, DataAccessException {
            if (rs.next()) {
                {{tableBeanClassName}} bean = new {{tableBeanClassName}}();
                {{#columns}}
                bean.{{setMethodName}}(rs.{{resultSetGetMethodName}}("{{tableColumnName}}"));
                {{/columns}}
                return bean;
            }/*end if(rs.next())*/
            return null;
        }
    };

    @CacheEvict(value = "{{tableBeanClassName}}", allEntries = true, beforeInvocation = true)
    public int insert({{tableBeanClassName}} {{tableBeanInstanceName}}) {
        return jdbcTemplate.update("{{insertSql}}", {{&insertPreparedStatementSetter}});
    }

    {{#hasPrimaryKey}}
    @CacheEvict(value = "{{tableBeanClassName}}", allEntries = true, beforeInvocation = true)
    public int update({{tableBeanClassName}}  {{tableBeanInstanceName}}) {
        return jdbcTemplate.update("{{{updateSql}}}", {{&updatePreparedStatementSetter}});
    }

    @Cacheable(value = "{{tableBeanClassName}}")
    public {{tableBeanClassName}} findByPk({{findByPkMethodParams}}){
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_SQL).append(" WHERE 1=1");
        {{#primaryKeyColumns}}
        sql.append(" AND {{tableColumnName}}=?");
        {{/primaryKeyColumns}}

        return findOneBySql(sql.toString(), {{getFindByPkQueryParams}});
    }
    {{/hasPrimaryKey}}

    @CacheEvict(value = "{{tableBeanClassName}}", allEntries = true, beforeInvocation = true)
    public int delete({{tableBeanClassName}} {{tableBeanInstanceName}}) {
        return jdbcTemplate.update("{{{deleteSql}}}", {{deleteParams}});
    }

    @Cacheable(value = "{{tableBeanClassName}}")
    public List<{{tableBeanClassName}}> findBySql(String sql, Object... params) {
        return jdbcTemplate.query(sql, ROW_MAPPER, params);
    }

    @Cacheable(value = "{{tableBeanClassName}}")
    public {{tableBeanClassName}} findOneBySql(String sql, Object... params) {
        return jdbcTemplate.query(sql, params, RESULT_SET_EXTRACTOR);
    }

    @CacheEvict(value = "{{tableBeanClassName}}", allEntries = true, beforeInvocation = true)
    public int updateBySql(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    @Cacheable(value = "{{tableBeanClassName}}")
    public <T> T queryOneBySql(String sql, ResultSetExtractor<T> resultSetExtractor, Object... params) {
        return jdbcTemplate.query(sql, params, resultSetExtractor);
    }

    @Cacheable(value = "{{tableBeanClassName}}")
    public <T> List<T> queryListBySql(String sql, RowMapper<T> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, rowMapper, params);
    }
}