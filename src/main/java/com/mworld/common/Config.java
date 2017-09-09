package com.mworld.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${cob}")
    public String FILES_COB;
}
