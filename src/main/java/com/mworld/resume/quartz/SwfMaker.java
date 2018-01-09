package com.mworld.resume.quartz;

import com.mworld.common.Config;
import com.mworld.common.util.DocConverter;
import com.mworld.resume.po.Resume;
import com.mworld.resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class SwfMaker {

    @Autowired
    private Config config;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private DocConverter docConverter;

    @Scheduled(fixedRate = 50000)
    public void doWordToSwf() {
        List<Resume> list = resumeService.waitTransSwf(config.MAX_TRANS_SWF_NU);
        if (CollectionUtils.isEmpty(list))
            return;
        List<Resume> result = new ArrayList<>();
        for (Resume resume : list) {
            docConverter.init(config.FILES_UPLOAD_COB + config.RESUME_UPLOAD_PACKAGE + File.separator + resume.getFilePath() + File.separator + resume.getDestName());
            docConverter.setOutPath(config.SWF_FILE_PATH);
            if (docConverter.convert())
                list.add(resume);
        }
        resumeService.updateBatchSwf(result);

    }
}
