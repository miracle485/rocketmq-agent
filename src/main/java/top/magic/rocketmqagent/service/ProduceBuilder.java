package top.magic.rocketmqagent.service;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProduceBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduceBuilder.class);
    private final Map<String, DefaultMQProducer> producerMap = Maps.newConcurrentMap();

    public DefaultMQProducer getMQProducer(String url, String ak, String sk) throws MQClientException {
        if (StringUtils.isBlank(url)) {
            return null;
        }


        if (producerMap.containsKey(url)) {
            return producerMap.get(url);
        }
        return generateMQProducer(url, ak, sk);
    }

    public void shutDownAllProducer() {
        for (Map.Entry<String, DefaultMQProducer> mqProducerEntry : producerMap.entrySet()) {
            mqProducerEntry.getValue().shutdown();
        }
        LOGGER.info("rocketmq producer has bean down");
    }


    private synchronized DefaultMQProducer generateMQProducer(String url, String ak, String sk) throws MQClientException {
        DefaultMQProducer mqProducer = createDefaultProducer(ak, sk);
        mqProducer.setNamesrvAddr(url);

        mqProducer.start();
        producerMap.put(url, mqProducer);
        return mqProducer;
    }

    private DefaultMQProducer createDefaultProducer(String ak, String sk) {
        if (StringUtils.isBlank(ak) || StringUtils.isBlank(sk)) {
            return new DefaultMQProducer();
        }
        return new DefaultMQProducer(new AclClientRPCHook(new SessionCredentials(ak, sk)));
    }
}
