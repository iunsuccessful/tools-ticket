package iunsuccessful.tools.tools.ticket;

import iunsuccessful.tools.tools.ticket.collect.CollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LiQZ on 2017/11/12.
 */
@Controller
public class TestAction {

    private static final Logger logger = LoggerFactory.getLogger(TestAction.class);

    @Autowired
    private CollectService collectService;

    @ResponseBody
    @RequestMapping("generate-ips")
    public String generateIps() {
        collectService.generateIps();
        return "success";
    }

    @ResponseBody
    @RequestMapping("click-btn")
    public String clickBtn() {
        collectService.clickBtn();
        return "success";
    }

    @ResponseBody
    @RequestMapping("test")
    public String test() {
        logger.info("");
        collectService.findIpList();
        return "success";
    }

}
