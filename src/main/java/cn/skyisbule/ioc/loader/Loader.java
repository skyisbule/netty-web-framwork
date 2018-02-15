package cn.skyisbule.ioc.loader;

import cn.skyisbule.ioc.annotation.Controller;
import cn.skyisbule.ioc.annotation.Service;
import cn.skyisbule.ioc.annotation.Url;
import cn.skyisbule.ioc.bean.BeanFactory;
import cn.skyisbule.server.Server;
import cn.skyisbule.util.UrlClassLoader;
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

    BeanFactory beanFactory = new BeanFactory();

    //项目目录
    String MainClassPath;

    public void init() throws Exception {
        //获取扫描路径 即项目根目录
        //getPath();
        //ceshi
        MainClassPath = "C:\\Users\\ZDNF\\Desktop\\temp\\netty_web_framework\\target\\test-classes";
        //扫描类
        scan(MainClassPath);

    }


    //递归扫描开发者项目目录下的所有类
    private void scan(String path) throws Exception {
        File file = new File(path);
        String[] files = file.list();
        for (String eachFileName : files){
            File eachFile = new File(path+"\\"+eachFileName);
            if (eachFile.isDirectory()){
                scan(path+"\\"+eachFileName);
            }else if (eachFileName.endsWith(".class")){
                log.info("成功扫描到bean:{}",eachFileName);
                //新建一个类的实例
                newInstance(path,eachFileName);
            }
        }

    }

    //新建一个类的实例
    private void newInstance(String classPath,String fileName) throws Exception{
        //先把前边的路径去掉 即:将 c:\\~\\desktop\\cn.skyisbule.test.class转换为cn.skyisbule.test
        String packageName = classPath.replace(MainClassPath,"").replace("\\",".");
        //去掉.class后缀，拿到类名
        String className = fileName.substring(0,fileName.length()-6);
        String packageWithClassName = packageName.concat(className);
        if (packageWithClassName.startsWith("."))
            packageWithClassName = packageWithClassName.substring(1);
        Class clazz = Class.forName(packageName+"."+className);
        //检查有没有Controller或者Service注解
        if (clazz.isAnnotationPresent(Controller.class)||
                clazz.isAnnotationPresent(Service.class)) {
            log.debug("成功实例化对象:{}", className);
            //创建对象实例，并添加进beanFactory
            beanFactory.addInstance(packageWithClassName, clazz.newInstance());
        }
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
