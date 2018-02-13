package cn.skyisbule.mvc.http;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 * 用于cookie操作
 */
@Setter
@Slf4j
public class Cookie {

    private String  name     = null;
    private String  value    = null;
    private String  domain   = null;
    private String  path     = "/";
    private long    maxAge   = -1;
    private boolean secure   = false;
    private boolean httpOnly = false;

    private String getCookie(){
        return name+"="+value+";";
    }

    private String getDomain(){
        return domain==null?
                "":" domain="+domain+";";
    }

    private String getmMaxAge(){
        return " max-age="+String.valueOf(maxAge)+";";
    }

    private String getPath(){
        return " path="+path;
    }

    @Override
    public String toString(){
        return getCookie()+getDomain()+getmMaxAge()+getPath();
    }
}
