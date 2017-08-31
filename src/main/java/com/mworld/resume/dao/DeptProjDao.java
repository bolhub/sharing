package com.mworld.resume.dao;

import com.mworld.resume.po.Department;
import com.mworld.resume.po.Project;
import com.mworld.resume.po.Resume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptProjDao {

    public Integer saveDpt(String dptName);

    public Integer saveDptPo(Department dpt);

    public Integer savePro(Project pro);

    public Integer saveDptPrj(@Param("dptId") Integer dptName, @Param("proId") Integer proId);

    public List<Project> findPrjOfDpt(@Param("dptId") Integer dptId, @Param("keyword") String keyword, @Param("start") Integer start, @Param("size") Integer size);

    public Integer findPrjOfDptCnt(@Param("dptId") Integer dptId, @Param("keyword") String keyword);

    public Integer findDptCntByName(String dptName);

    public Integer findPrjCntByName(String proName);

    public Integer findDptCntPrj(@Param("dptName") String dptName, @Param("proName") String proName);

    public Integer findDptPrjCnt(@Param("dptId") Integer dptId, @Param("proName") String proName);

    public Integer findDptIdByName(String dptName);

    public Integer findProIdByName(String proName);

    public Integer findDptProCnt(@Param("dptId") Integer dptId, @Param("proId") String proId);

    public Integer saveDptAndPros(@Param("dptId") Integer dptId, @Param("list") List<Integer> list);

    public Integer saveProAndDpts(@Param("proId") Integer proId, @Param("list") List<Integer> list);

    public List<Department> findDptsTotal();

    public List<Department> findDpts(@Param("dptName") String dptName, @Param("start") Integer start, @Param("size") Integer size);

    public Integer findDptsCnt(@Param("dptName") String dptName);

    public List<Resume> findResumeOfPro(@Param("ctrId") Integer ctrId, @Param("proId") Integer proId, @Param("keyword") String keyword, @Param("start") Integer start, @Param("size") Integer size);

    public Integer findResumeOfProCnt(@Param("ctrId") Integer ctrId, @Param("proId") Integer proId, @Param("keyword") String keyword);

    public Integer saveResProShip(@Param("dptPrjId") Integer dptPrjId, @Param("resumes") String[] resumes);

    public Integer findDptPrjId(@Param("dptId") Integer dptId, @Param("proId") Integer proId);

    public Integer changeDptName(@Param("dptId") Integer dptId, @Param("dptName") String dptName);

    public Integer changeProName(@Param("proId") Integer proId, @Param("proName") String proName);

    public Integer changeDptProShip(@Param("dptId") Integer dptId, @Param("oldProId") Integer oldProId, @Param("newProId") Integer newProId);

    public Integer findExistOtherDpt(@Param("dptId") Integer dptId, @Param("proId") Integer proId);
}
