package cn.pumch.web.model;

import java.util.Date;

/**
 * 实际上这是一个BO和VO的综合体，有些field并不存在于数据库表中
 */
public class SignIn {

    private Long id;

    private Long courseId;

    private Long signerId;

    private Date signInTime;

    private Date scoreTime;

    private Integer score1;

    private Integer score2;

    private Integer score3;

    private Integer score4;

    private Integer score5;

    private Integer score6;

    private Integer score7;

    private Integer score8;

    /**
     * 签到学生姓名
     */
    private String nickName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 任课教师姓名
     */
    private String tName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSignerId() {
        return signerId;
    }

    public void setSignerId(Long signerId) {
        this.signerId = signerId;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Date scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Integer getScore3() {
        return score3;
    }

    public void setScore3(Integer score3) {
        this.score3 = score3;
    }

    public Integer getScore4() {
        return score4;
    }

    public void setScore4(Integer score4) {
        this.score4 = score4;
    }

    public Integer getScore5() {
        return score5;
    }

    public void setScore5(Integer score5) {
        this.score5 = score5;
    }

    public Integer getScore6() {
        return score6;
    }

    public void setScore6(Integer score6) {
        this.score6 = score6;
    }

    public Integer getScore7() {
        return score7;
    }

    public void setScore7(Integer score7) {
        this.score7 = score7;
    }

    public Integer getScore8() {
        return score8;
    }

    public void setScore8(Integer score8) {
        this.score8 = score8;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
}