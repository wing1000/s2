package fengfei.ucm.registry;

public enum ChainExecuteType {
    NonReturnValue(0), ReturnValue(1);

    private int type;

    private ChainExecuteType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
