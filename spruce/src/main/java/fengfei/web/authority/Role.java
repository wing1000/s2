package fengfei.web.authority;

/**
 */
public enum Role {
        Guest("Guest", 0),
        LoggedIn("LoggedIn", 100),
        Admin("admin", 200),
        Developer("developer", 300),
        SuperAdmin("super admin", 400), ;

    private String desc;
    private int code;

    private Role(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return ordinal();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static void main(String[] args) {
        System.out.println(SuperAdmin.ordinal());
        System.out.println(Admin.ordinal());
        System.out.println(SuperAdmin.ordinal());
        System.out.println(Admin.ordinal());
    }

}
