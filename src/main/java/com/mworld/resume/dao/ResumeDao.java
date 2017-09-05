package com.mworld.resume.dao;

import com.mworld.resume.po.Resume;
import com.mworld.resume.vo.ResumeMapVo;
import com.mworld.resume.vo.ResumeRequestVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResumeDao {
    public List<Resume> getResumeList(@Param("options") ResumeRequestVo options, @Param("start") int start, @Param("size") int size);

    public Integer addResume(ResumeRequestVo resume);

    public Resume findResumeFileById(String id);

    public Integer getResumesCnt(@Param("options") ResumeRequestVo options);

    public List<ResumeMapVo> findResumeDetail(@Param("options") ResumeRequestVo options, @Param("start") Integer start, @Param("size") Integer size);

    public Integer findResumeDetailCnt(@Param("options") ResumeRequestVo options);

    public List<ResumeMapVo> findAllowResumes(@Param("options") ResumeRequestVo options, @Param("start") Integer start, @Param("size") Integer size);

    public Integer findAllowResumesCnt(@Param("options") ResumeRequestVo options);

    public ResumeMapVo findResumeById(@Param("id") String id);

    public Integer updateResume(ResumeRequestVo requestVo);
}
