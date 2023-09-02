package com.zxh.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 17:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class QuartzTests {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void testDeleteJob() {
        boolean result = false;
        try {
            result = scheduler.deleteJob(new JobKey("postScoreRefreshJob", "communityJobGroup"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result);
    }
}
