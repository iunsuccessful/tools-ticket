package iunsuccessful.tools.tools.ticket.collect.crawl;

/**
 * IP 信息
 * Created by LiQZ on 2017/11/12.
 */
public class IPInfo {

    private String ipAddress;
    private int port;

    public IPInfo() {
    }

    public IPInfo(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

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
        return "IPInfo{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                '}';
    }
}
