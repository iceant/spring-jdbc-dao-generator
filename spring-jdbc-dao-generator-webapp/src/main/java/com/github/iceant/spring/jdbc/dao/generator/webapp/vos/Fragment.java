package com.github.iceant.spring.jdbc.dao.generator.webapp.vos;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Fragment implements Serializable {
    private Map<String, Object > content = new ConcurrentHashMap<>();

    public Fragment put(String name, Object value){
       content.put(name, value);
       return this;
    }

    public Map<String, Object> getContent() {
        return content;
    }
}
