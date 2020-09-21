package com.github.iceant.spring.jdbc.dao.generator.webapp.controller;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.RockerModel;
import com.fizzed.rocker.runtime.RockerBootstrap;
import com.fizzed.rocker.runtime.StringBuilderOutput;
import com.github.iceant.spring.jdbc.dao.generator.webapp.vos.Fragment;
import com.github.iceant.xrocker.spring.boot.starter.RockerProperties;
import com.github.iceant.xrocker.spring.boot.starter.SpringReloadingRockerBootstrap;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = {"/fragments"})
public class FragmentController {
    final RockerBootstrap rockerBootstrap;
    final ApplicationContext applicationContext;
    final Environment environment;
    final Boolean isDevelopMode;

    public FragmentController(Environment environment, ApplicationContext applicationContext) {
        this.environment = environment;
        this.rockerBootstrap = rockerBootstrap(environment);
        this.applicationContext = applicationContext;
        this.isDevelopMode = isDevelopMode();
    }

    public String getTemplateDirectory(){
        return environment.getProperty("fragment.spring.rocker.template-directory", environment.getProperty("spring.rocker.template-directory"));
    }

    public Boolean isDevelopMode(){
        return "true".equalsIgnoreCase(environment.getProperty("spring.rocker.is-develop-mode"));
    }

    public RockerBootstrap rockerBootstrap(Environment environment) {
        RockerProperties properties = new RockerProperties();
        String templateDirectory = environment.getProperty("fragment.spring.rocker.template-directory", environment.getProperty("spring.rocker.template-directory"));
        String outputDirectory = environment.getProperty("fragment.spring.rocker.output-directory", environment.getProperty("spring.rocker.output-directory"));
        String classDirectory = environment.getProperty("fragment.spring.rocker.class-directory", environment.getProperty("spring.rocker.class-directory"));
        Boolean reloading = environment.getProperty("fragment.spring.rocker.reloading", Boolean.class, environment.getProperty("spring.rocker.reloading", Boolean.class, Boolean.FALSE));
        Boolean optimize = environment.getProperty("fragment.spring.rocker.optimize", Boolean.class, environment.getProperty("spring.rocker.optimize", Boolean.class, Boolean.FALSE));
        Boolean discardLogicWhitespace = environment.getProperty("fragment.spring.rocker.discard-logic-whitespace", Boolean.class, environment.getProperty("spring.rocker.discard-logic-whitespace", Boolean.class, Boolean.TRUE));
        String postProcessing = environment.getProperty("fragment.spring.rocker.postProcessing", environment.getProperty("spring.rocker.postProcessing"));

        properties.setTemplateDirectory(templateDirectory);
        properties.setOutputDirectory(outputDirectory);
        properties.setClassDirectory(classDirectory);
        properties.setPrefix("");
        properties.setSuffix("");
        properties.setReloading(reloading);
        properties.setOptimize(optimize);
        properties.setDiscardLogicWhitespace(discardLogicWhitespace);
        properties.setPostProcessing(postProcessing);

        RockerBootstrap bootstrap = new SpringReloadingRockerBootstrap(properties);
        return bootstrap;
    }

    private String render(String templatePath) {
        RockerModel model = rockerBootstrap.model(templatePath);
        BindableRockerModel bindableRockerModel = new BindableRockerModel(templatePath, model.getClass().getCanonicalName(), model);
        try {
            return bindableRockerModel.render(StringBuilderOutput.FACTORY).toString();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    @RequestMapping(path = {"/{name}"})
    public Object fragment(@PathVariable("name") String fragmentName) {
        Fragment fragment = new Fragment();
        String fragmentRelativePath = "fragments/" + fragmentName;
        if(isDevelopMode){
            loadFromTemplate(fragment, fragmentRelativePath);
        }else{
            loadFromClass(fragment, fragmentRelativePath);
        }

        return fragment;
    }

    private Fragment loadFromClass(Fragment fragment, String fragmentRelativePath){
        try {
            List<String> controllers = new ArrayList<>();
            Resource[] resources = applicationContext.getResources("classpath:/" + fragmentRelativePath + "/**");
            for (Resource resource : resources) {
                String url = resource.getURL().toString();
                if (url.contains("$Template.class") || url.contains("$PlainText.class")) {
                    continue;
                }
                int pos = url.indexOf(fragmentRelativePath);
                if (pos != -1) {
                    String relativePath = url.substring(pos);
                    relativePath = translatePath(relativePath);
                    if (relativePath.indexOf(".") == -1) continue;
                    String path = relativePath.substring(fragmentRelativePath.length() + 1);
                    if (relativePath.endsWith(".js")) {
                        controllers.add(path);
                    }
                    fragment.put(path, render(relativePath));
                }
            }
            fragment.put("scripts", controllers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fragment;
    }

    private Fragment loadFromTemplate(Fragment fragment, String fragmentRelativePath){
        try {
            List<String> controllers = new ArrayList<>();
            Resource[] resources = applicationContext.getResources("classpath:/templates/" + fragmentRelativePath + "/**");
            int fragmentRelativePathLength = fragmentRelativePath.length();
            for (Resource resource : resources) {
                String url = resource.getURL().toString();
                int pos = url.indexOf(fragmentRelativePath);
                if (pos != -1) {
                    String relativePath = url.substring(pos + fragmentRelativePathLength + 1);
                    if (relativePath.endsWith("/")) continue;/*folder*/
                    if (relativePath.endsWith(".js")) {
                        controllers.add(relativePath);
                    }
                    fragment.put(relativePath, render(fragmentRelativePath+"/"+relativePath));
                }
            }
            fragment.put("scripts", controllers);
        }catch (Exception err){
            throw new RuntimeException(err);
        }
        return fragment;
    }

    private String translatePath(String relativePath) {
        if(relativePath==null) return relativePath;
        int pos = relativePath.indexOf("_dot_html.class");
        if(pos!=-1){
            return relativePath.substring(0, pos)+".html";
        }
        pos = relativePath.indexOf("_dot_js.class");
        if(pos!=-1){
            return relativePath.substring(0, pos)+".js";
        }
        pos = relativePath.indexOf("_dot_css.class");
        if(pos!=-1){
            return relativePath.substring(0, pos)+".css";
        }
        pos = relativePath.indexOf("_dot_");
        if(pos!=-1){
            int dot = relativePath.indexOf(".", pos);
            if(dot!=-1) {
                return relativePath.substring(0, pos) + "." + relativePath.substring(pos+ 5 /*"_dot_".length()*/, dot);
            }
        }
        return relativePath;
    }
}
