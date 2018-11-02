package cn.pumch.web.enums;

public enum UserType {

    MT("mt", "管理教师"), T("t", "任课教师"), S("s", "学生");

    UserType(String role_name, String role_desc) {
        this.role_name = role_name;
        this.role_desc = role_desc;
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

    private String role_name;
    private String role_desc;
}
