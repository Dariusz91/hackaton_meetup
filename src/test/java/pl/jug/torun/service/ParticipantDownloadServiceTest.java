package pl.jug.torun.service;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;

@Ignore
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParticipantDownloadServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ParticipantDownloadService participantDownloadService;
}