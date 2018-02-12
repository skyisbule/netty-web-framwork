package cn.skyisbule.mvc.http;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 * 用于cookie操作
 */
@Getter
@Slf4j
public class Cookie {

    private String  name     = null;
    private String  value    = null;
    private String  domain   = null;
    private String  path     = "/";
    private long    maxAge   = -1;
    private boolean secure   = false;
    private boolean httpOnly = false;

}
