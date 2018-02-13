package cn.skyisbule.ioc.loader;

import cn.skyisbule.ioc.annotation.Url;
import cn.skyisbule.ioc.bean.BeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skyisbule on 2018/2/13.
 * loader类 用于扫描项目目录 将扫描到的类 递交给beanFactory
 */
@Slf4j
public class Loader {

    List<String> classesPath = new ArrayList<>();

    String MainClassPath;

    public void init() throws Exception {
        //获取扫描路径 即项目根目录
        getPath();
        //扫描类
        scan(MainClassPath);

    }


    //递归扫描开发者项目目录下的所有类
    private void scan(String path) throws Exception {
        File file = new File(path);
        String[] files = file.list();
        for (String eachFilePath : files){
            File eachFile = new File(path+"\\"+eachFilePath);
            if (eachFile.isDirectory()){
                scan(path+"\\"+eachFilePath);
            }else{
                log.info("成功扫描到bean:{}",eachFilePath);
                //新建一个类的实例
                newInstance(eachFilePath);
            }
        }

    }

    //新建一个类的实例
    private void newInstance(String classPath) throws Exception{
        //拿到类名
        String className = getClassName(classPath);
        log.info(className);
    }

    //获取项目文件根目录 方便扫描
    private void getPath(){
        String MainPath;
        String[] test = System.getProperty("java.class.path").split(";");
        Arrays.stream(test).
                filter(path->!path.endsWith("jar"))
                .filter(path->path.indexOf("test")<2)
                .forEach(pathTemp->setMainClassPath(pathTemp));
    }

    private void setMainClassPath(String path){
        this.MainClassPath = path;
    }

    //把文件的.class去掉，获取类名
    private String getClassName(String className){
        int pos = className.lastIndexOf(".");
        return className.substring(0,pos);
    }
}
