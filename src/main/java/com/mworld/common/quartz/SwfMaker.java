package com.mworld.common.quartz;

import com.mworld.common.Config;
import com.mworld.common.util.DocConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SwfMaker {

    @Autowired
    private Config config;

    @Scheduled(fixedRate = 50000)
    public void doWordToSwf() {
        DocConverter docConverter = new DocConverter("D:/随书附带说明.doc");
//        docConverter.setOutPath(config.SWF_FILE_PATH);
//        docConverter.setSwfToolsPath(config.SWF_TOOL_PATH);
        docConverter.convert();
    }
}
