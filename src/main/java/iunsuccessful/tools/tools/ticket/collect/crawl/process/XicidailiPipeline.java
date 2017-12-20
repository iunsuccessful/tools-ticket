package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.pipeline.Pipeline;
import iunsuccessful.tools.tools.ticket.collect.crawl.IPInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理数据
 * Created by LiQZ on 2017/11/12.
 */
@Service
public class XicidailiPipeline implements Pipeline<Xicidaili> {
    
    private static final Logger logger = LoggerFactory.getLogger(XicidailiPipeline.class);

    @Override
    public void process(Xicidaili bean) {
        logger.info("XicidailiPipeline process {}", bean);
        if (CollectionUtils.isEmpty(bean.getIpList())) {
            logger.error("find xicidaili not data");
        }
        List<IPInfo> infoList = bean.getIpList().stream().map(xicidailiItem -> {
            IPInfo info = new IPInfo();
            BeanUtils.copyProperties(xicidailiItem, info);
            return info;
        }).collect(Collectors.toList());

        // 加入到列表里面
        infoList.forEach(System.out::println);
    }
}
