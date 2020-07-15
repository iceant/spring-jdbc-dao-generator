package com.github.iceant.spring.jdbc.dao.generator.webapp.controller;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.RenderingException;
import com.fizzed.rocker.RockerModel;
import com.fizzed.rocker.runtime.OutputStreamOutput;
import com.fizzed.rocker.runtime.RockerBootstrap;
import com.github.iceant.spring.jdbc.dao.generator.webapp.utils.MimeTypeUtil;
import com.github.iceant.xrocker.spring.boot.starter.RockerProperties;
import com.github.iceant.xrocker.spring.boot.starter.SpringReloadingRockerBootstrap;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(path = {"/dynamic"})
public class DynamicController {
    static final int PATH_PREFIX_LENGTH = "/dynamic/".length();
    final RockerBootstrap rockerBootstrap;

    public DynamicController(Environment environment) {
        this.rockerBootstrap = rockerBootstrap(environment);
    }

    public RockerBootstrap rockerBootstrap(Environment environment) {
        RockerProperties properties = new RockerProperties();

        String templateDirectory = environment.getProperty("dynamic.spring.rocker.template-directory", environment.getProperty("spring.rocker.template-directory"));
        String outputDirectory = environment.getProperty("dynamic.spring.rocker.output-directory", environment.getProperty("spring.rocker.output-directory"));
        String classDirectory = environment.getProperty("dynamic.spring.rocker.class-directory", environment.getProperty("spring.rocker.class-directory"));
        Boolean reloading = environment.getProperty("dynamic.spring.rocker.reloading", Boolean.class, Boolean.TRUE);
        Boolean optimize = environment.getProperty("dynamic.spring.rocker.optimize", Boolean.class, Boolean.TRUE);
        Boolean discardLogicWhitespace = environment.getProperty("dynamic.spring.rocker.discard-logic-whitespace", Boolean.class, Boolean.TRUE);
        String postProcessing = environment.getProperty("dynamic.spring.rocker.postProcessing", environment.getProperty("spring.rocker.postProcessing"));

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

    private void render(String templatePath, HttpServletResponse response) {
        RockerModel model = rockerBootstrap.model(templatePath);
        BindableRockerModel bindableRockerModel = new BindableRockerModel(templatePath, model.getClass().getCanonicalName(), model);
        try {
            response.setContentType(MimeTypeUtil.getInstance().getMimetype(templatePath));
            OutputStreamOutput output = bindableRockerModel.render((contentType, charsetName) -> {
                try {
                    response.setCharacterEncoding(charsetName);
                    return new OutputStreamOutput(contentType, response.getOutputStream(), charsetName);
                } catch (IOException e) {
                    throw new RenderingException(e.getMessage(), e);
                }
            });

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    @RequestMapping(path = {"/**"})
    public void dynamic(HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        String contextPath = requestUri.substring(request.getContextPath().length()+1);
        render(contextPath, response);
    }
}
