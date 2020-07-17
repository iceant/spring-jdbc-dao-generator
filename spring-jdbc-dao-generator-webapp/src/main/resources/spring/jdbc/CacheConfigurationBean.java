package {{packageName}};

import {{tableBeanPackage}}.*;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(
            {{#tableBeans}}
            new ConcurrentMapCache({{className}}.class.getSimpleName()),
            {{/tableBeans}}
            new ConcurrentMapCache("global")
        ));
        return simpleCacheManager;
    }

    @Bean("keyGenerator")
    public KeyGenerator keyGenerator () {
        return new KeyGenerator() {
            public Object generate (Object target, Method method, Object...params){
                return target.getClass().getSimpleName() + "_"
                        + method.getName() + "_"
                        + StringUtils.arrayToDelimitedString(params, "_");
            }
        };
    }
}
