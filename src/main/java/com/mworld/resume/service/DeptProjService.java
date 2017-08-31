package com.mworld.resume.service;

import com.mworld.resume.po.Department;
import com.mworld.resume.po.Project;
import com.mworld.resume.po.Resume;

import java.util.List;

public interface DeptProjService {

    public Integer saveDpt(String dptName);

    public Integer saveDptPo(Department dpt);

    public Integer savePro(Project pro);

    public Integer saveDptPrjMid(Integer dptId, Integer proId);

    public Integer saveDptPrjMid(Integer dptId, String proName);

    public List<Project> findPrjOfDpt(Integer dptId, String keyword, Integer start, Integer size);

    public Integer findPrjOfDptCnt(Integer dptId, String keyword);

    public Integer findDptCntByName(String dptName);

    public Integer findPrjCntByName(String proName);

    public Integer findDptCntPrj(String dptName, String proName);

    public Integer findDptPrjCnt(Integer dptId, String proName);

    public Integer findDptIdByName(String dptName);

    public Integer findProIdByName(String proName);

    public Integer findDptProCnt(Integer dptId, String proId);

    public Integer saveDptAndPros(Integer dptId, List<Integer> list);

    public Integer saveProAndDpts(Integer proId, List<Integer> list);

    public List<Department> findDptsTotal();

    public List<Department> findDpts(String dptName, Integer start, Integer size);

    public Integer findDptsCnt(String dptName);

    public List<Resume> findResumeOfPro(Integer ctrId, Integer proId, String keyword, Integer start, Integer size);

    public Integer findResumeOfProCnt(Integer ctrId, Integer proId, String keyword);

    public Integer saveResProShip(Integer dptId, Integer proId, String[] resumes);

    public Integer findDptPrjId(Integer dptId, Integer proId);

    public Integer changeDptName(Integer dptId, String dptName);

    public Integer changeProName(Integer dptId, Integer proId, String proName);
}
