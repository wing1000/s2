package fengfei.web.authority;

/**
 */
public enum AuthorityType {

        Create("创建", 0),
        Read("查看", 1),
        Modify("修改", 2),
        Delete("删除", 3), ;

    private String name;
    private int index;

    private AuthorityType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
