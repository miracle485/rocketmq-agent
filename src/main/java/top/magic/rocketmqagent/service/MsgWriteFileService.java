package top.magic.rocketmqagent.service;

import org.springframework.stereotype.Service;
import top.magic.rocketmqagent.model.MsgInfo;
import top.magic.rocketmqagent.service.store.MsgFileQueue;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class MsgWriteFileService {
    //@Resource
    private MsgFileQueue msgFileQueue;

    public boolean writeMsg2FileWhenSendFail(MsgInfo msgInfo) {

        return true;
    }
}
