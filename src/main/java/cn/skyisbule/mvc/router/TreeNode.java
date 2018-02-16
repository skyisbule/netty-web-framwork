package cn.skyisbule.mvc.router;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyisbule on 2018/2/16.
 * 构造url tree的单个节点
 */
@NoArgsConstructor
public class TreeNode {

    //当前节点的名称 例如/a/b/c 则可以为b
    private String name;
    //孩子节点们
    private List<TreeNode> child = new ArrayList<TreeNode>();
    //这个属性用来控制
    String fullUrl;

    protected TreeNode(String name,String fullUrl){
        this.name    = name;
        this.fullUrl = fullUrl;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public boolean add(TreeNode node){
        child.add(node);
        return true;
    }

    public boolean addOne(TreeNode node){
        child.add(node);
        return true;
    }

    public List<TreeNode> getChilds(){
        return child;
    }

    public void setFullUrl(String url){
        this.fullUrl=url;
    }

    public String getFullUrl(){
        return this.fullUrl;
    }
}
