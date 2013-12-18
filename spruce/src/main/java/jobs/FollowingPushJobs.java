package jobs;

import play.jobs.Every;
import play.jobs.Job;

@Every("1h")
public class FollowingPushJobs extends Job<String> {

    @Override
    public void doJob() throws Exception {

        super.doJob();
    }

}
