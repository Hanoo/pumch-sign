package cn.pumch.web.model;

import java.util.Date;

public class CourseScore {
    private Long id;

    private Long courseId;

    private Long scorerId;

    private Date scoreTime;

    private Integer score;

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

    public Long getScorerId() {
        return scorerId;
    }

    public void setScorerId(Long scorerId) {
        this.scorerId = scorerId;
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Date scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}