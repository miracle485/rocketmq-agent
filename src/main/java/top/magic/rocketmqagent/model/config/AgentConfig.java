package top.magic.rocketmqagent.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "agent.rocketmq.config")
@Configuration
public class AgentConfig {
    private int maxThreadPool;
    private int maxFileNum;

    public int getMaxThreadPool() {
        return maxThreadPool;
    }

    public int getMaxFileNum() {
        return maxFileNum;
    }

    public void setMaxThreadPool(int maxThreadPool) {
        this.maxThreadPool = maxThreadPool;
    }

    public void setMaxFileNum(int maxFileNum) {
        this.maxFileNum = maxFileNum;
    }
}
