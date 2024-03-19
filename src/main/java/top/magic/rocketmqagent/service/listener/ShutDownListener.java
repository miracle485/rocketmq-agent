package top.magic.rocketmqagent.service.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;
import top.magic.rocketmqagent.service.ProduceBuilder;

import javax.annotation.Resource;

/**
 * @author = wangyilin29
 * @date = 2024/1/25
 **/
@Service
public class ShutDownListener implements ApplicationListener<ContextClosedEvent> {
    @Resource
    private ProduceBuilder mqProducer;


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        mqProducer.shutDownAllProducer();
    }
}
