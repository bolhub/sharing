package com.mworld.resume.service.impl;

import com.mworld.common.PageUtil;
import com.mworld.resume.dao.ResumeDao;
import com.mworld.resume.po.Resume;
import com.mworld.resume.service.ResumeService;
import com.mworld.resume.vo.ResumeMapVo;
import com.mworld.resume.vo.ResumeRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bolom on 2017/8/7.
 */
@Service
public class ResumeServiceImpl implements ResumeService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ResumeDao resumeDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Resume> getResumes(ResumeRequestVo resume, Integer start, Integer size) {
        PageUtil pageUtil = new PageUtil(start, size);
        return resumeDao.getResumeList(resume, pageUtil.getStart(), pageUtil.getSize());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addResume(ResumeRequestVo resume) {
        return resumeDao.addResume(resume);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Resume findResume(String id) {
        return resumeDao.findResumeFileById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getResumesCnt(ResumeRequestVo options) {
        return resumeDao.getResumesCnt(options);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ResumeMapVo> findResumeDetail(ResumeRequestVo options, Integer start, Integer size) {
        PageUtil pageUtil = new PageUtil(start, size);
        return resumeDao.findResumeDetail(options, pageUtil.getStart(), pageUtil.getSize());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findResumeDetailCnt(ResumeRequestVo options) {
        return resumeDao.findResumeDetailCnt(options);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ResumeMapVo> findAllowResumes(ResumeRequestVo options, Integer start, Integer size) {
        PageUtil pageUtil = new PageUtil(start, size);
        return resumeDao.findAllowResumes(options, pageUtil.getStart(), pageUtil.getSize());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findAllowResumesCnt(ResumeRequestVo options) {
        return resumeDao.findAllowResumesCnt(options);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResumeMapVo findResumeById(String id) {
        return resumeDao.findResumeById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateResume(ResumeRequestVo requestVo) {
        return resumeDao.updateResume(requestVo);
    }
}
