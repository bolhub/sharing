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
    private String dptName;
    private String proName;

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

}
