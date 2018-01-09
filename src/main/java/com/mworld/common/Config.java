package com.mworld.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${uploadDiskDir}")
    public String FILES_UPLOAD_COB;

    @Value("${resumeUploadPackage}")
    public String RESUME_UPLOAD_PACKAGE;

    @Value("${tmpZipDir}")
    public String TMP_ZIP_DIR;

    @Value("${PDFTempPath}")
    public String PDF_TMP_PATH;

    @Value("${SWFPath}")
    public String SWF_FILE_PATH;

    @Value("${SWFServerPath}")
    public String SWF_SERVER_PATH;

    @Value("${SWFToolPath}")
    public String SWF_TOOL_PATH;

    @Value("${openOfficePort}")
    public Integer OPEN_OFFICE_PORT;

    @Value("${environment}")
    public Integer ENVIRONMENT;

    @Value("${maxTransToSWF}")
    public Integer MAX_TRANS_SWF_NU;

}
