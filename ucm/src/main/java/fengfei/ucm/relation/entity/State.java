package fengfei.ucm.relation.entity;

public enum State {
    Normal(0, "Normal"),
    Expired(1, "Expired"),
    Archived(2, "Archived"),
    Removed(3, "Removed");

    private int code;
    private String message;

    private State(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
