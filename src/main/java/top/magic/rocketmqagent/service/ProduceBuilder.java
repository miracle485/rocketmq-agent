package top.magic.rocketmqagent.service;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProduceBuilder {
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


    private synchronized DefaultMQProducer generateMQProducer(String url, String ak, String sk) throws MQClientException {
        DefaultMQProducer mqProducer = new DefaultMQProducer();
        mqProducer.setNamesrvAddr(url);

        mqProducer.start();
        producerMap.put(url, mqProducer);
        return mqProducer;
    }
}
