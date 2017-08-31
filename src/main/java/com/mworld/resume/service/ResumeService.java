package com.mworld.resume.service;

import com.mworld.resume.po.Resume;
import com.mworld.resume.vo.ResumeMapVo;
import com.mworld.resume.vo.ResumeRequestVo;

import java.util.List;

/**
 * Created by bolom on 2017/8/7.
 */
public interface ResumeService {

    public List<Resume> getResumes(ResumeRequestVo resume, Integer start, Integer size);

    public Integer addResume(ResumeRequestVo resumes);

    public Resume findResume(String id);

    public Integer getResumesCnt(ResumeRequestVo options);

    public List<ResumeMapVo> findResumeDetail(ResumeRequestVo options, Integer start, Integer size);

    public Integer findResumeDetailCnt(ResumeRequestVo options);

    public List<ResumeMapVo> findAllowResumes(ResumeRequestVo options, Integer start, Integer size);

    public Integer findAllowResumesCnt(ResumeRequestVo options);

    public ResumeMapVo findResumeById(String id);
}
