package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import iunsuccessful.tools.tools.ticket.collect.crawl.CollectServiceImpl;
import iunsuccessful.tools.tools.ticket.collect.crawl.IPInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
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
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiQZ on 2017/11/15.
 */
@Service
public class VotePipeline implements Pipeline<Vote> {

    private static final Logger logger = LoggerFactory.getLogger(VotePipeline.class);
    
    @Override
    public void process(Vote bean) {
//        logger.info("进入主页处理程序");
        String content = bean.getContent();
        Pattern pattern = Pattern.compile("token=(\\w+)\"");
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            String token = m.group(1);
            logger.info("打开主页，抓取 token 为：{}", token);
            Arrays.asList(1, 2, 3, 4).parallelStream().forEach(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) {
                    vote(bean.getId(), token);
                }
            });

            // 如果有 token 则点击投票
                // 设置代理
                // 点击投票
                // http://www.2b.cn/vote/hundred/top.php?token={token}

        } else {
            logger.info("没有找到 token");
            System.out.println(content);
        }
        // token 刷新有问题，时间设长一点
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 如果没有 token，继续访问主页
        String url = String.format("http://www.2b.cn/vote/hundred/digg.php?id=%d", bean.getId());
        HttpGetRequest request = new HttpGetRequest(url);
//                proxy = new HttpHost(ipInfo.getIpAddress(), ipInfo.getPort());
        DeriveSchedulerContext.into(request);

    }

    private void vote(Integer beanId, String token) {
        try {
                IPInfo ipInfo = CollectServiceImpl.validIPQueue.take();
                logger.info("获取到有效的 IP 为：{}", ipInfo);
                vote(ipInfo, beanId, token);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static final String TEST_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400";



    private boolean vote(IPInfo ipInfo, Integer id, String token) {

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(20000)
                .setConnectTimeout(20000)
                .setConnectionRequestTimeout(20000)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();

        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie(token, id+"");
        cookie.setDomain("www.2b.cn");
        cookieStore.addCookie(cookie);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setDefaultCookieStore(cookieStore)
                .build();

        HttpPost httpPost = new HttpPost("http://www.2b.cn/vote/hundred/top.php?token="+token);
        httpPost.addHeader("User-Agent", TEST_USER_AGENT);
        httpPost.addHeader("HOST", "www.2b.cn");
        httpPost.addHeader("Cookie", token+"="+id);

        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setProxy(new HttpHost(ipInfo.getIpAddress(), ipInfo.getPort()))
                .build();

        httpPost.setConfig(requestConfig);

        //建立一个NameValuePair数组，用于存储欲传送的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加参数
        params.add(new BasicNameValuePair("id","138"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
            String temp = new String(bytes, "utf-8");
            byte[] contentData = temp.getBytes("utf-8");
            String body = new String(contentData);
            if (body.length() < 50) {
                logger.info(body);
            } else {
                logger.info("访问失败");
//                CollectServiceImpl.validIPQueue.put(ipInfo);
            }
            if (body.contains("404")) {
                logger.info(body);
                logger.error("###########################################");
                logger.error("vote address is change");
                logger.error("###########################################");
                System.exit(0);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.info("投票失败~~");
            return false;
        }
        return false;
    }

}
