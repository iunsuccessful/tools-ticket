package iunsuccessful.tools.tools.ticket.vote;

import iunsuccessful.tools.tools.ticket.collect.crawl.IPInfo;

/**
 * Created by LiQZ on 2017/11/15.
 */
public interface VoteService {

    /**
     * 测试代理有没有用的
     */
    boolean testVote(IPInfo ipInfo);

    /**
     * 使用有用的代理增投票
     */
    void vote(IPInfo ipInfo);

    void startVote();

}
