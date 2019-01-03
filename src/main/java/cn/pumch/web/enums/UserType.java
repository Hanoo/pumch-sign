package cn.pumch.web.enums;

public enum UserType {

    MT("mt", "管理教师", 1l), T("t", "任课教师", 2l), S("s", "学生", 3l);

    UserType(String role_name, String role_desc, Long role_id) {
        this.role_name = role_name;
        this.role_desc = role_desc;
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_desc() {
        return role_desc;
    }

    public void setRole_desc(String role_desc) {
        this.role_desc = role_desc;
    }

    public Long getRole_id() {
        return role_id;
    }

    public static UserType getUserTypeByRoleName(String roleName) {
        UserType userType;
        switch (roleName) {
            case "mt" :
                userType = MT;
                break;
            case "t" :
                userType = T;
                break;
            default:
                userType = S;
        }
        return userType;
    }

    private String role_name;
    private String role_desc;
    private Long role_id;
}
