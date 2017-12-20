package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.alibaba.fastjson.JSONArray;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.JsonBean;

import java.util.List;

/**
 * Created by LiQZ on 2017/11/15.
 */
//@Gecco(matchUrl = "http://www.xdaili.cn/freeproxy", pipelines = {"consolePipeline", "xdailiPipeline"})
@Gecco(matchUrl = "http://www.xdaili.cn/ipagent/freeip/getFreeIps?page={page}&rows=10", pipelines = {"xdailiPipeline"})
public class Xdaili implements JsonBean {

    @RequestParameter
    private Integer page;

    @Request
    private HttpRequest request;

    @JSONPath("$.RESULT.total")
    private Integer total;

    @JSONPath("$.RESULT.rows")
    private JSONArray items;

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }
}
