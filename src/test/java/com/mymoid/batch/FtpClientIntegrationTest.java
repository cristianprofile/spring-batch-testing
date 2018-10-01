package com.mymoid.batch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FtpClientIntegrationTest {


    @Value("${file.report.user}")
    private String user;

    @Value("${file.report.password}")
    private String password;

    @Value("${file.report.server}")
    private String server;


    @Value("${file.report.port}")
    private String port;


    private FakeFtpServer fakeFtpServer;

    @Autowired
    private FtpClient ftpClient;

    @Before
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(user, password, "/data"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/fakeFile.txt", "asdadsad asdadsa erwer"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();
        ftpClient = new FtpClient(ftpClient.getServer(), fakeFtpServer.getServerControlPort(), ftpClient.getUser(),
                ftpClient.getPassword());
        ftpClient.open();
    }

    @After
    public void teardown() throws IOException {
        ftpClient.close();
        fakeFtpServer.stop();
    }

    @Test
    public void listFtpFiles() throws IOException {
        Collection<String> files = ftpClient.listFiles("");

        assertThat(files).contains("fakeFile.txt");
    }

    @Test
    public void downloadFtpFileToLocalFile () throws IOException {
        ftpClient.downloadFile("/fakeFile.txt", "downloaded_fake_local.txt");
        final File localFile = new File("downloaded_fake_local.txt");
        assertThat(localFile).exists();
        localFile.delete(); // cleanup  downloaded_fake_local.txtfile
    }

    @Test
    public void uploadLocalFileToSftpServer() throws URISyntaxException, IOException {
        File file = new File(getClass().getClassLoader().getResource("ftp/localFile.txt").toURI());

        ftpClient.putFileToPath(file, "/uploadFileToFtpText.txt");

        assertThat(fakeFtpServer.getFileSystem().exists("/uploadFileToFtpText.txt")).isTrue();
    }

}
