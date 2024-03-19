package top.magic.rocketmqagent.service;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.magic.rocketmqagent.model.MsgInfo;
import top.magic.rocketmqagent.model.MsgSendResponse;
import top.magic.rocketmqagent.model.errorcode.MsgSendErrCode;

import javax.annotation.Resource;

@Service
public class SendMsgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMsgService.class);
    @Resource
    private ProduceBuilder produceBuilder;
    @Resource
    private MsgWriteFileService msgWriteFileService;


    public MsgSendResponse sendMsg2Service(MsgInfo msgInfo) {
        try {
            DefaultMQProducer mqProducer = produceBuilder.getMQProducer(msgInfo.getClusterUrl(), msgInfo.getAccessKey(), msgInfo.getSecretKey());
            Message message = new Message();
            message.setTopic(msgInfo.getTopic());
            message.setBody(msgInfo.getMessageBody().getBytes());
            message.setTags(msgInfo.getTag());
            SendResult send = mqProducer.send(message);
            SendStatus sendStatus = send.getSendStatus();
            if (sendStatus == SendStatus.SEND_OK) {
                return MsgSendErrCode.success();
            }
        } catch (Exception e) {
            LOGGER.error("error when send msg to service , cluster is {} ,error is ", msgInfo.getClusterUrl(), e);
        }

        boolean result = msgWriteFileService.writeMsg2FileWhenSendFail(msgInfo);
        if (result) {
            return MsgSendErrCode.handleResponse(MsgSendErrCode.MSG_SEND_FAIL_BUT_WRITE_SUCCESS);
        }
        return MsgSendErrCode.handleResponse(MsgSendErrCode.MSG_SEND_FAIL_AND_WRITE_FAIL);
    }

}
