# 关于这个项目
这个项目是一个基于netty的mvc框架，简单、高效。<br>
不仅如此，它还很适合新手学习，因为它完全基于模块化设计，代码很少，而且几乎每几行就会有一行注释。<br>
# 简单的开发
依赖很少，功能简单明了，适合前后端分离的小项目。<br>
摆脱臃肿繁琐的xml，一切基于注解。<br>
内置json转换。<br>
直接运行jar部署。<br>
JDK8
# 配置
直接导入jar包即可。
# 快速入门
随便编写一个类
```java
@Controller
public class RequestTest {

    @Url("/")
    public String hello(){
       return "hello world";
    }

    public static void main(String[] a ) throws Exception {
        sky.creat()
                .setPort(9090)
                .run();
    }
}
```
访问localhost:9090，不出意外，你应该可以看到hello world了。
# 完整功能列表
## 请求映射
为一个类加上@Controller注解，在方法上加上@Url注解。
```java
@Url("/")
public String hello(){
   return "hello world";
}

@Url("/login")
public String hello(){
   return "hello world";
}
```
返回值可以是任意类型，默认会返回Object.toString。<br>
如果为void，则什么都不返回。
## 获取请求参数
获取请求参数有两种方法：
### 直接传参
```java
@Url("/login")
public String login(String id,String passwd){
   return id+passwd;
}
```
### 绑定实体
```java
public class User{
  String id;
  String passwd;
  //getter and setter
}

@Url("/login")
public String login(User user){
   return user.getId()+user.getPasswd();
}
```
### 获取原始请求类、响应类
```java
@Url("/login")
public void login(SkyRequest req,SkyResponse resp){
   //获取原始参数
   Map<String,List<Object>> map = req.getParam();
   //获取请求头
   Map<String,Object> headers   = req.getHeaders(); 
   //......略
}
```
## 获取和设置cookie
```java
//省略传参，传入SkyRequest req和SkyResponse resp
//获取
Cookie[] cookies = req.getCookies();
String   value   = req.getCookie("id");
//设置cookie
Cookie cookie = new Cookie("id","1");
resp.addCookie(cookie);
//....关于req和resp的更多方法以后再更。
```
## 静态资源
关于静态资源，如果目录下有index.html，则
```java
@Url("/")
public String hello(){
   return "index.html";
}
```
会返回资源目录，template目录下的index.html文件。<br>
普通资源则不用加载,放到static文件件下即可。<br>
若static文件夹下有css文件夹，在下边有test.css<br>
则访问localhost:9090/static/css/test.css即可看到内容。
## 处理json
使用@Json注解过的方法都会将返回值自动转为json格式，若使用@Json注解类，则代表该类下的所有方法都自动Json化。
## 重定向
```java
@Url("/redirect")
public String redirect(){
   return "redirect:http://www.baidu.com";
}
```
# todo
拦截器，日志输出，模板引擎，session支持，上传文件。
