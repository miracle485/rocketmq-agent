package top.magic.rocketmqagent.service.auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.magic.rocketmqagent.model.config.AgentConfig;

@Configuration
public class BeanAutoLoad {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanAutoLoad.class);

    @Bean
    public AgentConfig getMsgFile() {
        LOGGER.error("test");
        return null;
    }
}
