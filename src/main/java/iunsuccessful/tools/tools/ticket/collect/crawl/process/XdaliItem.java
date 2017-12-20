package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.annotation.JSONPath;

/**
 * Created by LiQZ on 2017/11/15.
 */
public class XdaliItem {

    @JSONPath("ip")
    private String ipAddress;

    @JSONPath("port")
    private int port;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "XdaliItem{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                '}';
    }
}
