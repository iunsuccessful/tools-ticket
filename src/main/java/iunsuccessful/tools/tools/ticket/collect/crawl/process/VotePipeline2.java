package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import iunsuccessful.tools.tools.ticket.collect.crawl.IPInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiQZ on 2017/11/15.
 */
@Service
public class VotePipeline2 implements Pipeline<Vote> {

    private static final Logger logger = LoggerFactory.getLogger(VotePipeline2.class);

    private List<String> tokens = new ArrayList<>();
    
    @Override
    public void process(Vote bean) {
        logger.info("进入主页处理程序");
        String content = bean.getContent();
        Pattern pattern = Pattern.compile("token=(\\w+)\"");
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            String token = m.group(1);
            logger.info("打开主页，抓取 token 为：{}", token);
            if (tokens.contains(token)) {
                logger.info("token 没有刷新");
            } else {
                // 如果有 token，访问投票页
                String url = String.format("http://www.2b.cn/vote/hundred/top.php?token=%s", token);
                HttpRequest currRequest = bean.getRequest();
                DeriveSchedulerContext.into(currRequest.subRequest(url));
            }

        } else {
            logger.info("没有找到 token");
            System.out.println(content);
        }
        // 如果没有 token，继续访问主页
        String url = String.format("http://www.2b.cn/vote/hundred/digg.php?id=%d", bean.getId());
        HttpGetRequest request = new HttpGetRequest(url);
        DeriveSchedulerContext.into(request);

    }

}
