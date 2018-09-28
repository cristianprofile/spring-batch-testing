package com.mymoid.batch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file.report")
public class FileProperties {

    private String directory;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
