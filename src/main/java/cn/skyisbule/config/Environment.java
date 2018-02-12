package cn.skyisbule.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by skyisbule on 2018/2/12.
 */
@AllArgsConstructor
@Getter
@Setter
public class Environment {
    private Environment(){

    }

    public String address      =  "127.0.0.1";
    public int port            =  80;
    public int BossThreadCount =  1;
    public int WorkThreadCount =  4;

    public static Environment create(){
        return new Environment();
    }


}
