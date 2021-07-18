package com.wenjun.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wen jun tang
 * @date 2021/7/14 11:45
 */
@Data
@Configuration
public class FileConfig {
    @Value("${file.ip}")
    private String ip;
    @Value("${server.port}")
    private Integer port;
    @Value("${file.enable}")
    private boolean enable;

    public String getUrlString(boolean https) {
        if (!enable) {
            try {
                InetAddress address;
                address = InetAddress.getLocalHost();
                ip = address.getHostAddress();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return https ? "https://" : "http://" + ip + ":" + port + "/";
    }

    public String getFileString(boolean https) {
        return getUrlString(https) + "file/";
    }
}
