package com.mworld.resume.service.impl;

import com.mworld.common.PageUtil;
import com.mworld.resume.dao.DeptProjDao;
import com.mworld.resume.po.Department;
import com.mworld.resume.po.Project;
import com.mworld.resume.po.Resume;
import com.mworld.resume.service.DeptProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptProjServiceImpl implements DeptProjService {

    @Autowired
    private DeptProjDao deptProjDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveDpt(String dptName) {
        return deptProjDao.saveDpt(dptName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveDptPo(Department dpt) {
        return deptProjDao.saveDptPo(dpt);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer savePro(Project pro) {
        return deptProjDao.savePro(pro);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveDptPrjMid(Integer dptId, Integer proId) {
        return deptProjDao.saveDptPrj(dptId, proId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveDptPrjMid(Integer dptId, String proName) {
        Integer proId = deptProjDao.findProIdByName(proName);
        if (proId != null && proId > 0) {

            return deptProjDao.saveDptPrj(dptId, proId);

        } else {
            Project project = new Project(proName);
            Integer cnt = deptProjDao.savePro(project);
            if (cnt != null && cnt > 0)
                return deptProjDao.saveDptPrj(dptId, project.getId());
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Project> findPrjOfDpt(Integer dptId, String keyword, Integer start, Integer size) {
        PageUtil pageUtil = new PageUtil(start, size);
        return deptProjDao.findPrjOfDpt(dptId, keyword, pageUtil.getStart(), pageUtil.getSize());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findDptCntByName(String dptName) {
        return deptProjDao.findDptCntByName(dptName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findPrjCntByName(String proName) {
        return deptProjDao.findPrjCntByName(proName);
    }

    public Integer findDptCntPrj(String dptName, String proName) {
        return deptProjDao.findDptCntPrj(dptName, proName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findDptIdByName(String dptName) {
        return deptProjDao.findDptIdByName(dptName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findProIdByName(String proName) {
        return deptProjDao.findProIdByName(proName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findDptProCnt(Integer dptId, String proId) {
        return deptProjDao.findDptProCnt(dptId, proId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveDptAndPros(Integer dptId, List<Integer> list) {
        return deptProjDao.saveDptAndPros(dptId, list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveProAndDpts(Integer proId, List<Integer> list) {
        return deptProjDao.saveProAndDpts(proId, list);
    }

    @Override
    public List<Department> findDptsTotal() {
        return deptProjDao.findDptsTotal();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Department> findDpts(String dptName, Integer start, Integer size) {
        PageUtil pageUtil = new PageUtil(start, size);
        return deptProjDao.findDpts(dptName, pageUtil.getStart(), pageUtil.getSize());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Resume> findResumeOfPro(Integer ctrId, Integer proId, String keyword, Integer start, Integer size) {
        PageUtil pageUtil = new PageUtil(start, size);
        return deptProjDao.findResumeOfPro(ctrId, proId, keyword, pageUtil.getStart(), pageUtil.getSize());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findDptPrjCnt(Integer dptId, String proName) {
        return deptProjDao.findDptPrjCnt(dptId, proName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findPrjOfDptCnt(Integer dptId, String keyword) {
        return deptProjDao.findPrjOfDptCnt(dptId, keyword);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findDptsCnt(String dptName) {
        return deptProjDao.findDptsCnt(dptName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findResumeOfProCnt(Integer ctrId, Integer proId, String keyword) {
        return deptProjDao.findResumeOfProCnt(ctrId, proId, keyword);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveResProShip(Integer dptId, Integer proId, String[] resumes) {
        Integer dpPrId = deptProjDao.findDptPrjId(dptId, proId);
        if (dpPrId == null || dpPrId <= 0)
            return null;
        return deptProjDao.saveResProShip(dpPrId, resumes);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findDptPrjId(Integer dptId, Integer proId) {
        return deptProjDao.findDptPrjId(dptId, proId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer changeDptName(Integer dptId, String dptName) {
        return deptProjDao.changeDptName(dptId, dptName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer changeProName(Integer dptId, Integer proId, String proName) {
        Integer existShip = deptProjDao.findExistOtherDpt(dptId, proId);
        if (existShip != null && existShip > 1) {
            Integer tarId = deptProjDao.findProIdByName(proName);
            if (tarId != null && tarId > 0) {
                return deptProjDao.changeDptProShip(dptId, proId, tarId);
            } else {
                Project project = new Project(proName);
                Integer cnt = deptProjDao.savePro(project);
                if (cnt != null && cnt > 0) {
                    return deptProjDao.changeDptProShip(dptId, proId, project.getId());
                }
                return null;
            }
        }
        return deptProjDao.changeProName(proId, proName);
    }
}
