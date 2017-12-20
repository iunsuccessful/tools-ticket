package iunsuccessful.tools.tools.ticket.collect.crawl.process;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by LiQZ on 2017/11/15.
 */
@Gecco(matchUrl = "http://www.2b.cn/vote/hundred/top.php?token={token}", pipelines = "voteClickPipeline")
public class VoteClick implements HtmlBean {



}
