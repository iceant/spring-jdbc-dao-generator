package com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class JavaBeanUtil {

    public static File getRoot(){
        File root = new File("");
        return root;
    }

    public static String packageNameToPath(String packageName){
        return packageName.replace('.', File.separatorChar);
    }

    public static String beanNameToClassName(String beanName){
        return beanName;
    }

    public static File saveToPath(JavaBean bean, File path, boolean overwrite){
        String packageName = bean.getPackageName();
        String packagePath = packageNameToPath(packageName);
        File saveToPath = new File(path, packagePath);
        if(!saveToPath.exists()){
            System.out.println("Making dir... "+ saveToPath.getAbsolutePath());
            saveToPath.mkdirs();
        }
        File saveToFile = new File(saveToPath, beanNameToClassName(bean.getClassName())+".java");
        if(saveToFile.exists() && !overwrite) return saveToFile;
        System.out.println("Generating ... "+saveToFile.getAbsolutePath());
        OutputStreamWriter os = null;
        try {
            FileOutputStream fos = new FileOutputStream(saveToFile);
            os = new OutputStreamWriter(fos, "UTF-8");
            os.write(bean.getSourceCode());
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
        return saveToFile;
    }
}
