package top.magic.rocketmqagent.service.auto;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.magic.rocketmqagent.model.config.AgentConfig;
import top.magic.rocketmqagent.model.file.FileSizeEnums;
import top.magic.rocketmqagent.service.store.MsgFileQueue;

@Configuration
public class BeanAutoLoad {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanAutoLoad.class);

    @Bean
    public MsgFileQueue getMsgFile(AgentConfig agentConfig) {

        LOGGER.error("test, filePath is {}", agentConfig.getFilePath());
        int fileSize = 1024 * 1024 * 1024;

        MsgFileQueue msgFileQueue = new MsgFileQueue(agentConfig.getFilePath(), fileSize);
        return null;
    }
}
