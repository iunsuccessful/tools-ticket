package iunsuccessful.tools.tools.ticket.collect.crawl;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.spider.SpiderBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 不配置 Spring 扫那个包
 * Created by LiQZ on 2017/8/15.
 */
@Service("springPipelineFactory")
public class ISpringPipelineFactory  implements PipelineFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public ISpringPipelineFactory() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Pipeline<? extends SpiderBean> getPipeline(String name) {
        try {
            Object bean = this.applicationContext.getBean(name);
            if(bean instanceof Pipeline) {
                return (Pipeline)bean;
            }
        } catch (NoSuchBeanDefinitionException var3) {
            System.out.println("no such pipeline : " + name);
        }

        return null;
    }

}

