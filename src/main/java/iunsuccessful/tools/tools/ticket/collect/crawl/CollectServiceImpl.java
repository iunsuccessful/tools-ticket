package iunsuccessful.tools.tools.ticket.collect.crawl;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import iunsuccessful.tools.tools.ticket.collect.CollectService;
import iunsuccessful.tools.tools.ticket.vote.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 收集 IP
 * 有两个队列
 * 一个队列放刚抓到的 IP
 * 一个放测试通过的 IP
 * 验证线程池
 * Created by LiQZ on 2017/11/12.
 */
@Service
public class CollectServiceImpl implements CollectService {
    
    private static final Logger logger = LoggerFactory.getLogger(CollectServiceImpl.class);

    @Autowired
    private ISpringPipelineFactory springPipelineFactory;

    @Autowired
    private VoteService voteService;

    public static ArrayBlockingQueue<IPInfo> collectQueue = new ArrayBlockingQueue(1000);

    public static ArrayBlockingQueue<IPInfo> validIPQueue = new ArrayBlockingQueue(10);

    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("valid-ip-pool-%d").setDaemon(true).build();
    ExecutorService parentExecutor = Executors.newFixedThreadPool(10, threadFactory);
    final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(parentExecutor);


    @Override
    public void generateIps() {
        crawl();
    }

    @Override
    public void clickBtn() {
        GeccoEngine.create()
                .pipelineFactory(springPipelineFactory)
                .classpath("iunsuccessful.tools.tools.ticket.collect.crawl.process")
                //开始抓取的页面地址
                .start("http://www.2b.cn/vote/hundred/digg.php?id=138")
                .proxy(true)
                //开启几个爬虫线程
                .thread(10)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(2000)
//                .loop(true)
                .mobile(false)
                .start();
    }

    @Override
    public void findIpList() {
        // 抓取代理
        crawl();
//        clickBtn();
//        // 验证代理
//        validIPs();
//        // 投票
//        vote();
    }


    private AtomicBoolean lock = new AtomicBoolean(false);

    public void crawl() {
        // 如果没运行，才可以执行下面代码
        if (lock.compareAndSet(false, true)) {
            try {
                crawl0();
            } finally {
                lock.set(false);
            }
        } else {
            throw new RuntimeException("爬虫正在运行~");
        }
    }

    private void crawl0() {

        // http://www.2b.cn/vote/hundred/digg.php?id=138&from=timeline&isappinstalled=0
        List<HttpRequest> requests = new ArrayList<>();

        HttpGetRequest home = new HttpGetRequest("http://www.2b.cn/vote/hundred/digg.php?id=138");
        HttpGetRequest kuaidaili = new HttpGetRequest("http://www.kuaidaili.com/free/");
        HttpGetRequest xdaili = new HttpGetRequest("http://www.xdaili.cn/ipagent/freeip/getFreeIps?page=1&rows=10");
        requests.add(home);
        requests.add(xdaili);

        GeccoEngine.create()
                .pipelineFactory(springPipelineFactory)
                .classpath("iunsuccessful.tools.tools.ticket.collect.crawl.process")
                //开始抓取的页面地址
                .start(requests)
                //开启几个爬虫线程
                .thread(10)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(2000)
//                .loop(true)
                .mobile(false)
                .start();
    }

    private void validIPs() {
        // 10 个线程验证
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
//                        logger.info("collectQueue 队列中，目前有数据 " + collectQueue.size());
                        try {
                            IPInfo ipInfo = collectQueue.take();
                            boolean result = voteService.testVote(ipInfo);
                            if (result) {
                                validIPQueue.put(ipInfo);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    private void vote() {
//        voteService.startVote();
//        while (true) {
//            logger.info("validIPQueue 队列中，目前有数据 {}", validIPQueue.size());
//            IPInfo ipInfo = null;
//            try {
//                ipInfo = validIPQueue.take();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            voteService.vote(ipInfo);
//        }

    }
}
