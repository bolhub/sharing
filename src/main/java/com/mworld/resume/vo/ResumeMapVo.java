package com.mworld.resume.vo;

import com.mworld.resume.po.Resume;

import java.util.List;

public class ResumeMapVo extends Resume {

    //    private List<DepartProjectVo> dps;
//
//    public List<DepartProjectVo> getDps() {
//        return dps;
//    }
//
//    public void setDps(List<DepartProjectVo> dps) {
//        this.dps = dps;
//    }
    private Integer dptId;
    private String dptName;//项目编制
    private String proName;//项目名
    private String ctrName;//人员编制
    private String sex;

    public Integer getDptId() {
        return dptId;
    }

    public void setDptId(Integer dptId) {
        this.dptId = dptId;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCtrName() {
        return ctrName;
    }

    public void setCtrName(String ctrName) {
        this.ctrName = ctrName;
    }
}
