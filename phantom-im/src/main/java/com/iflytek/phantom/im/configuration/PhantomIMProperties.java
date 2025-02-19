package com.iflytek.phantom.im.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Configuration;

import java.net.*;
import java.util.Enumeration;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "phantom.im")
@Data
public class PhantomIMProperties {

    private EngineProperties engine;
    private String networkInterface = "";
    private String ip;
    private String groupTail;

    @Autowired
    private InetUtils inetUtils;
//    @Autowired
//    private InetIPv6Utils inetIPv6Utils;

    public PhantomIMProperties() {
        this.engine = new EngineProperties();
    }

    @PostConstruct
    public void init() throws SocketException {
        if (StringUtils.isEmpty(this.ip)) {
            if (StringUtils.isEmpty(networkInterface)) {
                this.ip = this.inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            }
        } else {
            NetworkInterface netInterface = NetworkInterface
                    .getByName(networkInterface);
            if (null == netInterface) {
                throw new IllegalArgumentException(
                        "no such interface " + networkInterface);
            }
            Enumeration<InetAddress> inetAddress = netInterface.getInetAddresses();
            while (inetAddress.hasMoreElements()) {
                InetAddress currentAddress = inetAddress.nextElement();
                if (currentAddress instanceof Inet4Address
                        || currentAddress instanceof Inet6Address
                        && !currentAddress.isLoopbackAddress()) {
                    ip = currentAddress.getHostAddress();
                    break;
                }
            }

            if (StringUtils.isEmpty(ip)) {
                throw new RuntimeException("cannot find available ip from"
                        + " network interface " + networkInterface);
            }

        }

        if (StringUtils.isEmpty(this.groupTail)) {
            this.groupTail = this.ip.replace(".","%");
        }

    }

}
