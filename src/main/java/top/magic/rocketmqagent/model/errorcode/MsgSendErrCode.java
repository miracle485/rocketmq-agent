package top.magic.rocketmqagent.model.errorcode;

import top.magic.rocketmqagent.model.MsgSendResponse;

public enum MsgSendErrCode {
    CLUSTER_URL_IS_NULL(40001, "clusterUrl or  is null"),
    OK(40002, "send access"),
    MSG_SEND_FAIL_BUT_WRITE_SUCCESS(40003, "message send fail but write to disk success"),
    MSG_SEND_FAIL_AND_WRITE_FAIL(40004, "message send fail and write to disk fail");


    private int code;
    private String msg;

    private MsgSendErrCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static MsgSendResponse handleResponse(MsgSendErrCode code) {
        if (code == null) {
            throw new IllegalArgumentException("errorCode can't null when handle response");
        }
        return new MsgSendResponse(code.getCode(), code.getMsg());
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
