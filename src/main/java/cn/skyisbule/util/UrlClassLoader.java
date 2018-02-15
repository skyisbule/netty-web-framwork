package cn.skyisbule.util;


import cn.hutool.core.io.file.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/14.
 * 用于读取.class文件 生成一个 class实例
 */
@Slf4j
public class UrlClassLoader extends ClassLoader{

    public Class<?> loadClassFromDisk(String filePath,String fileName) throws Exception {
        byte[] classBytes = null;
        String path;
        try {
            log.debug("开始读取:{}",filePath+"\\"+fileName);
            FileReader reader = new FileReader(filePath+"\\"+fileName);
            classBytes = reader.readBytes();
            //Class clazz = super.defineClass(fileName,classBytes,0,classBytes.length);
            Class clazz = Class.forName("test.httpServer.RequestTest");
            return clazz;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
