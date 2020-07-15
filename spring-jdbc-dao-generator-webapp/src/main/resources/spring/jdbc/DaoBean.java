package {{packageName}};

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import {{repositoryBeanPackageName}}.{{repositoryBeanClassName}};
import {{tableBeanPackageName}}.{{tableBeanClassName}};

@Repository
public class {{daoBeanClassName}} extends {{repositoryBeanClassName}}{

    public {{daoBeanClassName}}(JdbcTemplate jdbcTemplate){
        super(jdbcTemplate);
    }
}