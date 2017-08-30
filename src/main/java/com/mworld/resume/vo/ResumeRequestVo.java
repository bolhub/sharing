package com.mworld.resume.vo;

import com.mworld.resume.po.Resume;

import java.util.Date;

/**
 * Created by bolom on 2017/8/8.
 */
public class ResumeRequestVo extends Resume {
    private Date from;
    private Date to;
    private Integer dptId;
    private Integer proId;
    private String keyword;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Integer getDptId() {
        return dptId;
    }

    public void setDptId(Integer dptId) {
        this.dptId = dptId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
