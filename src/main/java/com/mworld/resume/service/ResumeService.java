package com.mworld.resume.service;

import com.mworld.resume.po.Resume;
import com.mworld.resume.vo.ResumeMapVo;
import com.mworld.resume.vo.ResumeRequestVo;
import jdk.nashorn.internal.ir.IdentNode;

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

    public Integer updateResume(ResumeRequestVo requestVo);

    public List<ResumeMapVo> findUploadResumes(String uploaderId, String keyword, Integer start, Integer size);

    public Integer findUploadResumesCnt(String uploaderId, String keyword);

    public List<ResumeMapVo> findResFiles(String[] resIds);

    /**
     * 查找简历文件上传信息
     * @param id
     * @return
     */
    public Resume findDocById(String id);

    /**
     * 批量查询待生成SWF的WORD文档信息
     * @param size
     * @return
     */
    public List<Resume> waitTransSwf(Integer size);

    /**
     * 更新SWF生成状态
     * @param list
     * @return
     */
    public Integer updateBatchSwf(List<Resume> list);
}
