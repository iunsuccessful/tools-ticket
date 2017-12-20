package iunsuccessful.tools.tools.ticket.vote.impl;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import iunsuccessful.tools.tools.ticket.collect.crawl.CollectServiceImpl;
import iunsuccessful.tools.tools.ticket.collect.crawl.IPInfo;
import iunsuccessful.tools.tools.ticket.collect.crawl.ISpringPipelineFactory;
import iunsuccessful.tools.tools.ticket.vote.VoteService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiQZ on 2017/11/15.
 */
@Service
public class VoteServiceImpl implements VoteService {
    
    private static final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    @Autowired
    private ISpringPipelineFactory springPipelineFactory;


    public static final String TEST_URL = "http://www.azt365.com/";
    public static final String TEST_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400";

    RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();



    @Override
    public boolean testVote(IPInfo ipInfo) {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        HttpPost httpPost = new HttpPost(TEST_URL);
        httpPost.addHeader("User-Agent", TEST_USER_AGENT);

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
//            HttpEntity entity = response.getEntity();
//            byte[] bytes = EntityUtils.toByteArray(entity);
//            String temp = new String(bytes, "utf-8");
//            byte[] contentData = temp.getBytes("utf-8");
            logger.info("IP {} 有效", ipInfo.getIpAddress());
        } catch (Exception e) {
//            logger.info("IP {} 无效", ipInfo.getIpAddress());
            return false;
        }
        return true;
    }

    @Override
    public void vote(IPInfo ipInfo) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        HttpPost httpPost = new HttpPost("http://www.2b.cn/vote/hundred/corel9.php");
        httpPost.addHeader("User-Agent", TEST_USER_AGENT);
        httpPost.addHeader("HOST", "www.2b.cn");
        httpPost.addHeader("Cookie", "cb55c58b643e2018d4f600f6714c3fdb=138");

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
                System.out.println(body);
            } else {
                System.out.println("访问失败");
//                CollectServiceImpl.validIPQueue.put(ipInfo);
            }
            if (body.contains("404")) {
                System.out.println(body);
                logger.error("###########################################");
                logger.error("vote address is change");
                logger.error("###########################################");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("IP 无效");
        }
    }

    @Override
    public void startVote() {
        // http://www.2b.cn/vote/hundred/digg.php?id=138&from=timeline&isappinstalled=0

        GeccoEngine.create()
                .pipelineFactory(springPipelineFactory)
                .classpath("iunsuccessful.tools.tools.ticket.collect.crawl.process")
                //开始抓取的页面地址
                .start("http://www.2b.cn/vote/hundred/digg.php?id=138")
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(10000)
                .loop(true)
                .mobile(false)
                .start();
    }
}
