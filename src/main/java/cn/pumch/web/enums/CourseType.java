package cn.pumch.web.enums;

public enum CourseType {

    REQUIRED("1", "必修课"), OPTIONAL("0", "选修课");

    CourseType(String courseCode, String displayWord) {
        this.courseCode = courseCode;
        this.displayWord = displayWord;
    }

    private String courseCode;
    private String displayWord;

    public String getCourseCode() {
        return this.courseCode;
    }

    public String getDisplayWord() {
        return this.displayWord;
    }
}
