package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.annotation.HtmlField;

/**
 * <tr>

 <td data-title="IP">125.67.75.8</td><td data-title="PORT">9000</td>
 <td data-title="匿名度">高匿名</td>
 <td data-title="类型">HTTP</td>
 <td data-title="位置">中国 四川省 遂宁市 电信</td>
 <td data-title="响应速度">3秒</td>
 <td data-title="最后验证时间">2017-11-12 15:31:15</td>
 </tr>
 * Created by LiQZ on 2017/11/12.
 */
public class XicidailiItem {

    @HtmlField(cssPath = "[data-title=IP]")
    private String ipAddress;

    @HtmlField(cssPath = "[data-title=PORT]")
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
}
