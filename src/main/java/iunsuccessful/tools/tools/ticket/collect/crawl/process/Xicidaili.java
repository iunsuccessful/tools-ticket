package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * Created by LiQZ on 2017/11/12.
 */
@Gecco(matchUrl = "http://www.kuaidaili.com/free/", pipelines = "xicidailiPipeline")
public class Xicidaili implements HtmlBean {

    /* 前两年数据 */
    // 2017
    @HtmlField(cssPath="#list tbody tr")
    private List<XicidailiItem> ipList;

    public List<XicidailiItem> getIpList() {
        return ipList;
    }

    public void setIpList(List<XicidailiItem> ipList) {
        this.ipList = ipList;
    }
}
