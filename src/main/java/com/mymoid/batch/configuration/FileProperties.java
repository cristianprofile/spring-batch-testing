package com.mymoid.batch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file.report")
public class FileProperties {

    private String directory;
    private String server;
    private int port;
    private String user;
    private String password;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
