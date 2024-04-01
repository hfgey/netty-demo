package com.xhpcd.config;

import com.xhpcd.protocol.Serializer;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/13:27
 * @Description:
 */
public class Config {
    static Properties properties;
    static {
        properties = new Properties();
        InputStream ins = Config.class.getResourceAsStream("/xhpcd.properties");
        try {
            properties.load(ins);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Serializer.Algorithm getDefault(){
        String algorithm = properties.getProperty("Algorithem");
        if(algorithm == null){
            algorithm = "JAVA";
        }
        return Serializer.Algorithm.valueOf(algorithm);
    }
    public static int getAlgorithm() throws IOException {
        String algorithm = properties.getProperty("Algorithem");
        if(algorithm == null){
            algorithm = "JAVA";
        }
        return Serializer.Algorithm.valueOf(algorithm).ordinal();
    }
    public static int getPort() throws IOException {
        String property = properties.getProperty("server.port");
        if(property == null){
            property = "8080";
        }
        return Integer.parseInt(property);
    }

}
