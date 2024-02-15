package top.magic.rocketmqagent.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.magic.rocketmqagent.model.MsgInfo;
import top.magic.rocketmqagent.model.MsgSendResponse;
import top.magic.rocketmqagent.model.errorcode.MsgSendErrCode;
import top.magic.rocketmqagent.service.SendMsgService;

import javax.annotation.Resource;

@RestController
@RequestMapping("rocketmq")
public class RecMsgController {
    @Resource
    private SendMsgService msgService;

    @GetMapping("send")
    public MsgSendResponse sendMessage(MsgInfo msgInfo) {
        if (StringUtils.isBlank(msgInfo.getMessageBody()) || StringUtils.isBlank(msgInfo.getClusterUrl())) {
            return MsgSendErrCode.handleResponse(MsgSendErrCode.CLUSTER_URL_IS_NULL);
        }
        return msgService.sendMsg2Service(msgInfo);
    }
}
