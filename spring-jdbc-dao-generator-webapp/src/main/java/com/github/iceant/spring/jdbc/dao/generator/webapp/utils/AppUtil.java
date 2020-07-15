package com.github.iceant.spring.jdbc.dao.generator.webapp.utils;

import com.github.iceant.spring.jdbc.dao.generator.webapp.vos.JsonResponse;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.util.Date;
import java.util.function.Function;

public class AppUtil {

    private static SequenceGenerator sequenceGenerator = new SequenceGenerator(SequenceGenerator.createCustomEpoch(2020, 1, 1));

    public static File getAppHome(Class springBootApplicationClass) {
        ApplicationHome applicationHome = new ApplicationHome(springBootApplicationClass);
        return applicationHome.getDir();
    }

    public static long id(){
       return sequenceGenerator.nextId();
    }

    public static Date now(){
        return new Date();
    }

    public static JsonResponse makeResponse(Function fn, Object ... objects) {
        JsonResponse response= new JsonResponse();
        try{
           Object result = fn.apply(objects);

           response.setStatusCode(1);
           response.setStatus(true);
           response.setMessage("success");
           response.put("result", result);
        }catch (Exception err){
            response.setStatusCode(-1);
            response.setStatus(false);
            response.setMessage(SpringUtil.exceptionToString(err));
        }
        return response;
    }
}
