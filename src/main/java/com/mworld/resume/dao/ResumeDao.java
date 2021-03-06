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

    public List<ResumeMapVo> findUploadResumes(@Param("uploaderId") String uploaderId, @Param("keyword") String keyword, @Param("start") Integer start, @Param("size") Integer size);

    public Integer findUploadResumesCnt(@Param("uploaderId") String uploaderId, @Param("keyword") String keyword);

    public List<ResumeMapVo> findResFiles(@Param("resIds") String[] resIds);

    /**
     * 根据ID查找简历文件上传信息
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
