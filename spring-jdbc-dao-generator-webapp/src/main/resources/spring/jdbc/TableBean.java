package {{packageName}};

import java.io.Serializable;
import java.util.Objects;

public class {{className}} implements Serializable{

    public static final String TABLE = "{{tableName}}";
    {{#columns}}
    public static final String {{tableColumnNameUpperCase}}="{{tableColumnName}}";
    {{/columns}}

    {{#columns}}
    private {{javaFieldType}} {{javaFiledName}};
    {{/columns}}

    {{#columns}}
    public {{javaFieldType}} {{getMethodName}}(){
        return this.{{javaFiledName}};
    }

    public {{className}} {{setMethodName}}({{javaFieldType}} {{javaFiledName}}){
        this.{{javaFiledName}} = {{javaFiledName}};
        return this;
    }

    {{/columns}}

    @Override
    public boolean equals(Object o) {
        {{&equalsCode}}
    }

    @Override
    public int hashCode() {
        {{&hashCode}}
    }

}