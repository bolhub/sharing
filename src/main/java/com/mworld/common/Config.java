package com.mworld.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${uploadDiskDir}")
    public String FILES_COB;
}
