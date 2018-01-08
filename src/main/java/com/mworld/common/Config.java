package com.mworld.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${uploadDiskDir}")
    public String FILES_COB;

    @Value("${tmpZipDir}")
    public String TMP_ZIP_DIR;

    @Value("${SWFPath}")
    public String SWF_FILE_PATH;

    @Value("{SWFServerPath}")
    public String SWF_SERVER_PATH;

    @Value("{SWFToolPath}")
    public String SWF_TOOL_PATH;

    @Value("${openOfficePort}")
    public Integer OPEN_OFFICE_PORT;

    @Value("${environment}")
    public Integer ENVIRONMENT;

}
