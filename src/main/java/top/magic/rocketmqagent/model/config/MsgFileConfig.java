package top.magic.rocketmqagent.model.config;

import java.io.File;

public class MsgFileConfig {
    private MsgFileConfig() {}

    public static final int DEFAULT_FILE_SIZE = 1024 * 1024 * 1024;
    public static final String DEFAULT_FILE_PATH = "~" + File.separator + "store" + File.separator + "rocketmq-agent";
    public static final String DEFAULT_STATUS_PATH = DEFAULT_FILE_PATH + File.separator + "stat.json";
}
