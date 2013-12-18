package fengfei.fir.model;

import java.util.HashMap;

public class Done extends HashMap<String, Object> {

    public static enum Status {
        Success, Fail, Error
    }

    private static final long serialVersionUID = 1L;
    final static String KeyMsg = "msg";
    final static String KeyStatus = "status";
    public String msg;
    public Status status;


    protected Done() {
        setStatus(Status.Success);
        setMsg(Status.Success.name());
    }

    public Done(String msg, Status status) {
        super();
        setMsg(msg);
        setStatus(status);
    }

    protected Done(Status status) {
        super();
        setStatus(status);
        setMsg(status.name());
    }

    public Done(String msg) {
        super();
        setMsg(msg);
        setStatus(Status.Success);
    }

    public String getMsg() {
        return msg = (String) get(KeyMsg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
        super.put(KeyMsg, msg);
    }

    public Status getStatus() {
        return status = (Status) get(KeyStatus);
    }

    public Object put(String key, Object value) {
        boolean keyExisted = key.equals(KeyMsg) ||
                key.equals(KeyStatus) ||
                key.equals(Status.Error.name()) ||
                key.equals(Status.Error.name().toLowerCase()) ||
                key.equals(Status.Fail.name()) ||
                key.equals(Status.Fail.name().toLowerCase()) ||
                key.equals(Status.Success.name()) ||
                key.equals(Status.Success.name().toLowerCase());
        if (keyExisted) {
            throw new RuntimeException(key +" is reserved keyword!");
        } else {
            return super.put(key, value);
        }

    }

    public void setStatus(Status status) {
        this.status = status;
        super.put(KeyStatus, status);
        super.put(Status.Success.name().toLowerCase(), status == Status.Success ? true : false);
    }
}
