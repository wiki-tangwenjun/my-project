package com.study.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

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
