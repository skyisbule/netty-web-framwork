package cn.skyisbule.mvc.router;

import cn.skyisbule.ioc.bean.BeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by skyisbule on 2018/2/16.
 * 整个mvc框架的路由控制中心
 */
@Slf4j
public class Router {

    //初始化根节点
    private TreeNode rootNode            = new TreeNode("/","");
    //拿到路由map的引用
    private Map<String,Method> methodMap = BeanFactory.methodMap;
    //路由的set，去除重复路由
    private Set<String> routesSet        = new HashSet<>();

    public void initRoute(){
        creatTree();
    }

    private void creatTree(){
        //创建一个当前要处理的节点
        TreeNode fatherNode = rootNode;
        //遍历一下每个节点 构造tree
        for (Map.Entry<String,Method> entry : methodMap.entrySet()){
            //重置fatherNode
            fatherNode = rootNode;
            String url = checkRoute(entry.getKey());
            //如果不是模板的话直接退出
            if (isTemplate(url)){
                continue;
            }
            //先处理一下重复问题
            if (!routesSet.add(url)){
                log.error("检测到有重复url:{}",url);
                continue;
            }
            //拿到需要处理的节点数
            int count = getMarkCount(url);
            //如果只需要处理一次 比如 /user
            if (count==1){
                //如果标记是 “/” 单独处理一下 代表首页
                if (url.equals("/")){
                    //设置一下代表首页请求不是空的
                    rootNode.setFullUrl("/");
                    continue;
                }
                TreeNode tempNode = new TreeNode(url.substring(1),url);
                log.info("成功构造url树节点:{}",url);
                fatherNode.addRoot(tempNode);
                continue;
            }
            //如果不止一个节点
            String nodes[] = url.substring('/').split("/");
            for (String node : nodes){
                TreeNode tempNode = new TreeNode(node,url);
                tempNode =  fatherNode.add(fatherNode,tempNode);
                //切换父节点
                fatherNode = tempNode;
            }
        }
    }

    //用来格式化请求路由
    private String checkRoute(String url){
        TreeNode fatherNode = new TreeNode();
        if (url.length()==0){
            url = "/";
        }
        return url;
    }

    //判断是否是url模板格式
    private boolean isTemplate(String url){
        char[] chars = url.toCharArray();
        for (char temp : chars){
            if (temp=='*'||temp=='{')
                return true;
        }
        return false;
    }


    //用来获得一个url里有多少个节点
    private int getMarkCount(String url){
        char[] characters =url.toCharArray();
        int count=0;
        for (int i = 0;i<characters.length;i++){
            if (characters[i]=='/'){
                count++;
            }
        }
        return count;
    }

}
