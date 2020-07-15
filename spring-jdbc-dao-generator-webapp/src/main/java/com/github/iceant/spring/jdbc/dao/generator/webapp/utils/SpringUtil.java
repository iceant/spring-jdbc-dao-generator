package com.github.iceant.spring.jdbc.dao.generator.webapp.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.CacheControl;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class SpringUtil implements ApplicationContextAware {
    private static final SpringUtil instance = new SpringUtil();

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static SpringUtil getInstance() {
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) {
        applicationContext = ac;
    }

    //////////////////////////////////////////////////////////////////////////
    ////
    public static String getRootPath() {
        // get SpringApplication path
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        return path;
    }

    public static File getAppHome(Class springBootApplicationClass) {
        ApplicationHome applicationHome = new ApplicationHome(springBootApplicationClass);
        return applicationHome.getDir();
    }

    public static String[] getBeanNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    //////////////////////////////////////////////////////////////////////////
    //// Property Helper
    public static String getProperty(String name, String defaultValue) {
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext==null) return defaultValue;
        return getApplicationContext().getEnvironment().getProperty(name, defaultValue);
    }

    public static String getProperty(String name) {
        return getApplicationContext().getEnvironment().getProperty(name);
    }

    public static <T> T getProperty(String name, Class<T> tClass) {
        return getApplicationContext().getEnvironment().getProperty(name, tClass);
    }

    public static <T> T getProperty(String name, Class<T> tClass, T defaultValue) {
        return getApplicationContext().getEnvironment().getProperty(name, tClass, defaultValue);
    }

    //////////////////////////////////////////////////////////////////////////
    //// Message Helper
    public static String getMessage(String code, Locale locale, Object... objects) {
        return getApplicationContext().getMessage(code, objects, locale);
    }

    public static String getMessage(String code) {
        return getApplicationContext().getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public static String getMessageWithParam(String code, Object... objects) {
        return getApplicationContext().getMessage(code, objects, LocaleContextHolder.getLocale());
    }

    public static String getMessageWithParamAndDefaultValue(String code, String defaultValue, Object... objects) {
        return getApplicationContext().getMessage(code, objects, defaultValue, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, String defaultValue, Locale locale, Object... objects) {
        return getApplicationContext().getMessage(code, objects, defaultValue, locale);
    }

    //////////////////////////////////////////////////////////////////////////
    //// Bean Helper
    public static <T> T getBean(Class<T> tClass) {
        return getApplicationContext().getBean(tClass);
    }

    public static <T> T getBean(Class<T> tClass, Object... args) {
        return getApplicationContext().getBean(tClass, args);
    }

    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    public static <T> T getBean(String name, Object... args) {
        return (T) getApplicationContext().getBean(name, args);
    }

    public static <T> T getBean(String name, Class<T> tClass) {
        return getApplicationContext().getBean(name, tClass);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> tClass){
        return getApplicationContext().getBeansOfType(tClass);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> aClass){
        return getApplicationContext().getBeansWithAnnotation(aClass);
    }

    //////////////////////////////////////////////////////////////////////////
    //// Request Helper
    public static HttpServletRequest getHttpServletRequet() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getResponse();
        }
        return null;
    }

    public static List<String> getRequestParameterNames() {
        HttpServletRequest request = getHttpServletRequet();
        ArrayList<String> names = new ArrayList<>();
        for(Enumeration<String> nameEnu = request.getParameterNames(); nameEnu.hasMoreElements();){
            String name = nameEnu.nextElement();
            names.add(name);
        }
        return names;
    }

    public static Object getRequestAttribute(String name) {
        return getHttpServletRequet().getAttribute(name);
    }

    public static void setRequestAttribute(String name, Object value){
        getHttpServletRequet().setAttribute(name, value);
    }

    public static Enumeration<String> getRequestAttributeNames() {
        return getHttpServletRequet().getAttributeNames();
    }

    public static List<String> getRequestAttributeNameList() {
        Enumeration<String> names = getRequestAttributeNames();
        List<String> result = new ArrayList<>();
        for (; names.hasMoreElements(); ) {
            result.add(names.nextElement());
        }
        return result;
    }

    public static <T> T attrValue(String name) {
        return (T) getHttpServletRequet().getAttribute(name);
    }

    public static Integer attrInt(String name, Integer defaultValue) {
        Integer value = (Integer) getHttpServletRequet().getAttribute(name);
        if (value == null) return defaultValue;
        return value;
    }

    public static Object field(String name, String field) {
        Object object = attrValue(name);
        if (object == null) return null;
        Class objectClass = object.getClass();
        try {
            Field objectField = objectClass.getField(field);
            return objectField.get(object);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        try {
            String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
            Method method = objectClass.getMethod(methodName);
            return method.invoke(object);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        }

        return null;
    }

    /* 以 objectName.fieldName 格式获取 request.attribute 中的对象属性 */
    public static Object field(String key) {
        int pos = key.indexOf(".");
        if (pos != -1) {
            String objectName = key.substring(0, pos);
            String fieldName = key.substring(pos + 1);
            return field(objectName, fieldName);
        }
        return null;
    }

    public static String getContextPath() {
        return getHttpServletRequet().getContextPath();
    }

    public static String getRelativePath(String url) {
        if (url == null || url.length() < 1) return "";
        String contextPath = getHttpServletRequet().getContextPath();
        contextPath = contextPath.endsWith("/") ? contextPath : contextPath + "/";
        url = url.startsWith("/") ? url.substring(1) : url;
        StringBuilder sb = new StringBuilder();
        sb.append(contextPath).append(url);
        return sb.toString();
    }

    public static String paramString(String name, String defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        return value;
    }

    public static String paramString(String name) {
        return getHttpServletRequet().getParameter(name);
    }

    public static Integer paramInt(String name, Integer defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value);
        }catch (NumberFormatException nfe){
            return defaultValue;
        }
    }

    public static Integer paramInt(String name) {
        return paramInt(name, null);
    }

    public static Long paramLong(String name, Long defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return Long.parseLong(value);
        }catch (NumberFormatException nfe){
            return defaultValue;
        }
    }

    public static Long paramLong(String name) {
        return paramLong(name, null);
    }

    public static Float paramFloat(String name, Float defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return Float.parseFloat(value);
        }catch (NumberFormatException nfe){
            return defaultValue;
        }
    }

    public static Float paramFloat(String name) {
        return paramFloat(name, null);
    }

    public static Double paramDouble(String name, Double defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return Double.parseDouble(value);
        }catch (NumberFormatException nfe){
            return defaultValue;
        }
    }

    public static Double paramDouble(String name) {
        return paramDouble(name, null);
    }

    public static Boolean paramBoolean(String name, Boolean defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        return Boolean.parseBoolean(value);
    }

    public static Boolean paramBoolean(String name){
        return paramBoolean(name, null);
    }


    public static Date paramDate(String name, String fmt, Date defaultValue) {
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return new SimpleDateFormat(fmt).parse(value);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date paramDate(String name, String fmt) {
        return paramDate(name, fmt, null);
    }

    public static Date paramDate(String name, Date defaultValue) {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return yyyyMMdd.parse(value);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date paramDate(String name) {
        return paramDate(name, (Date) null);
    }

    public static Date paramDateTime(String name, Date defaultValue) {
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value = getHttpServletRequet().getParameter(name);
        if (value == null) return defaultValue;
        try {
            return yyyyMMddHHmmss.parse(value);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date paramDateTime(String name) {
        return paramDateTime(name, (Date) null);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        if(date==null) return null;
        return yyyyMMdd.format(date);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date==null) return null;
        return yyyyMMddHHmmss.format(date);
    }

    public static String formatDate(Date date, String fmt) {
        if(date==null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
        return simpleDateFormat.format(date);
    }

    public static String formatDate(Date date, String fmt, Locale locale){
        if(date==null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt, locale);
        return simpleDateFormat.format(date);
    }

    //////////////////////////////////////////////////////////////////////////
    //// Session Helper
    public static HttpSession getHttpSession() {
        return getHttpServletRequet().getSession();
    }

    public static HttpSession getHttpSession(Boolean create) {
        return getHttpServletRequet().getSession(create);
    }

    public static HttpSession getHttpSession(boolean value) {
        return getHttpServletRequet().getSession(value);
    }


    public static <T> T getSessionAttribute(String key) {
        return (T) getHttpSession().getAttribute(key);
    }

    public static void setSessionAttribute(String key, Object value) {
        getHttpSession(true).setAttribute(key, value);
    }

    public static void removeSessionAttribute(String key) {
        getHttpSession().removeAttribute(key);
    }

    public static Enumeration<String> getSessionAttributeNames() {
        return getHttpSession().getAttributeNames();
    }

    //////////////////////////////////////////////////////////////////////////
    ////
    public static List<String> getActivedProfiles() {
        return Arrays.asList(getApplicationContext().getEnvironment().getActiveProfiles());
    }

    public static boolean isProfileActived(String profile) {
        String[] activedProfiles = getApplicationContext().getEnvironment().getActiveProfiles();
        for (String ap : activedProfiles) {
            if (ap.equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////
    ////
    public final static String getIpAddress(HttpServletRequest request) {
        if(request==null) return "0.0.0.0";
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    //////////////////////////////////////////////////////////////////////////
    //// CSRF Helper
    public static CsrfToken getCsrfToken() {
        CsrfToken token = new HttpSessionCsrfTokenRepository().loadToken(getHttpServletRequet());
        if (token == null) {
            token = (CsrfToken) getHttpServletRequet().getAttribute("_csrf");
        }
        return token;
    }

    public static String getCsrfTokenValue() {
        CsrfToken token = getCsrfToken();
        if (token == null) return "";
        return token.getToken();
    }

    public static String getCsrfTokenHeaderName() {
        CsrfToken token = getCsrfToken();
        if (token == null) return "";
        return token.getHeaderName();
    }

    public static String getCsrfTokenParameterName() {
        CsrfToken token = getCsrfToken();
        if (token == null) return "";
        return token.getParameterName();
    }

    //////////////////////////////////////////////////////////////////////////
    //// Stream Helper
    public static String readAsString(InputStream inputStream, String charset, int bufferSize){
        if(inputStream==null) return null;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(charset);
        }catch (Exception err){
            throw new RuntimeException(err);
        }
    }

    //////////////////////////////////////////////////////////////////////////
    //// Resource Helper
    public static Resource getResource(String url){
        return applicationContext.getResource(url);
    }

    public static Resource[] getResources(String url) {
        try {
            return applicationContext.getResources(url);
        } catch (IOException e) {
        }
        return new Resource[]{};
    }

    public static String readResourceAsString(Resource resource){
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readAsString(inputStream, "UTF-8", 1024);
    }

    public static String readResourceAsString(String url){
        Resource resource = getResource(url);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readAsString(inputStream, "UTF-8", 1024);
    }

    //////////////////////////////////////////////////////////////////////////
    //// Security Helper
    public static boolean hasPermission(String objectType, Serializable objectId, Object permission){
        //https://www.baeldung.com/spring-security-create-new-custom-security-expression
        PermissionEvaluator permissionEvaluator = getBean(PermissionEvaluator.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return permissionEvaluator.hasPermission(authentication, objectId, objectType, permission);
    }

    //////////////////////////////////////////////////////////////////////////
    //// Spring Expression
    public static <T> T parseExpression(String expression, ParserContext parserContext, EvaluationContext evaluationContext){
        //https://docs.spring.io/spring/docs/3.0.x/reference/expressions.html
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression exp = expressionParser.parseExpression(expression, parserContext);
        return (T) exp.getValue(evaluationContext);
    }

    public static void setupCache(int seconds){
        String headerValue = CacheControl.maxAge(seconds, TimeUnit.SECONDS).getHeaderValue();
        getHttpServletResponse().addHeader("Cache-Control", headerValue);
    }

    public static <T> T newInstance(Class<T> tClass){
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDateFormatByLocale(){
        Locale locale = LocaleContextHolder.getLocale();
        if(locale.getLanguage().startsWith("zh")){
            return "yyyy-MM-dd";
        }else if(locale.toString().equalsIgnoreCase("en_US")){
            return "MM/dd/yyyy";
        }else if(locale.getLanguage().startsWith("en")){
            return "dd/MM/yyyy";
        }else{
            return "yyyy-MM-dd";
        }
    }

    public static String getDateTimeFormatByLocale(){
        Locale locale = LocaleContextHolder.getLocale();
        if(locale.getLanguage().startsWith("zh")){
            return "yyyy-MM-dd HH:mm:ss";
        }else if(locale.toString().equalsIgnoreCase("en_US")){
            return "MM/dd/yyyy HH:mm:ss";
        }else if(locale.getLanguage().startsWith("en")){
            return "dd/MM/yyyy HH:mm:ss";
        }else{
            return "yyyy-MM-dd HH:mm:ss";
        }
    }

    public static void setupHttpCache(long milliseconds){
        HttpServletRequest request = getHttpServletRequet();
        HttpServletResponse response = getHttpServletResponse();

        long clientLastModified = request.getDateHeader("If-Modified-Since");
        if(clientLastModified + milliseconds > new Date().getTime()){
            response.setStatus(304);
            return;
        }
        response.setContentType("text/html;charset=gb2312");
        response.setDateHeader("Last-Modified", new Date().getTime());
        response.setDateHeader("Expires", new Date().getTime() + milliseconds);
        response.setHeader("Cache-Control", "max-age="+milliseconds/1000);//告诉浏览器缓存60秒
    }

    public static String exceptionToString(Exception err) {
        if (err == null) return "";
        final Charset charset = StandardCharsets.UTF_8;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = null;
        try {
            ps = new PrintStream(baos, true, charset.name());
            err.printStackTrace(ps);
            return new String(baos.toByteArray(), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return err.toString();
    }
}
