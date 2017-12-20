package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import iunsuccessful.tools.tools.ticket.collect.crawl.CollectServiceImpl;
import iunsuccessful.tools.tools.ticket.collect.crawl.IPInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by LiQZ on 2017/11/15.
 */
@Service
public class XdailiPipeline implements Pipeline<Xdaili> {

    private static final Logger logger = LoggerFactory.getLogger(XdailiPipeline.class);

    @Override
    public void process(Xdaili bean) {
        // 解析 json 内容
        for (int i = 0; i < bean.getItems().size(); i++) {
            JSONObject jo = bean.getItems().getJSONObject(i);
            if (jo.containsKey("ip") && jo.containsKey("port")) {
                // 直接写入文件吧
                //Get the file reference
//                Path path = Paths.get("D:/a/output.txt");

                //Use try-with-resource to get auto-closeable writer instance

                try {
                    String ip = jo.getString("ip");
                    int port = jo.getInteger("port");
//                    writeFile(new IPInfo(ip, port));
//                    CollectServiceImpl.collectQueue.put(new IPInfo(ip, port));
                    CollectServiceImpl.validIPQueue.put(new IPInfo(ip, port));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        logger.info("当前页码：{}", bean.getPage());

        if (bean.getPage() * 10 < bean.getTotal()) {
            String url = String.format("http://www.xdaili.cn/ipagent/freeip/getFreeIps?page=%d&rows=10", bean.getPage()+1);
            HttpRequest currRequest = bean.getRequest();
            DeriveSchedulerContext.into(currRequest.subRequest(url));
        }

    }

    private void writeFile(IPInfo ipInfo) {

        Path path = Paths.get("E:\\Anonymous\\Documents\\project\\tools-ticket\\src\\main\\resources\\proxys");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(ipInfo.getIpAddress()+":"+ipInfo.getPort()+"\n");
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
