package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by LiQZ on 2017/11/15.
 */
@Gecco(matchUrl = "http://www.2b.cn/vote/hundred/digg.php?id={id}", pipelines = "votePipeline", timeout=20000)
public class Vote implements HtmlBean {

    @RequestParameter
    private Integer id;

    @HtmlField(cssPath = "body")
    private String content;

    @Request
    private HttpRequest request;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }
}
