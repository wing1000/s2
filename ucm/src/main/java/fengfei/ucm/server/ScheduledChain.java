package fengfei.ucm.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduledChain implements ServerChain {
	private static final Logger logger = LoggerFactory
			.getLogger(ScheduledChain.class);
	ScheduledExecutorService scheduledExecutor;

	@Override
	public void init(ClassPathXmlApplicationContext context) {
		scheduledExecutor = Executors.newScheduledThreadPool(1);

	}

	@Override
	public void start(ClassPathXmlApplicationContext context) {
		scheduledExecutor.schedule(new Runnable() {

			@Override
			public void run() {
				logger.info("for daemon threads.");

			}
		}, 1, TimeUnit.DAYS);

	}

	@Override
	public void restart(ClassPathXmlApplicationContext context) {
		
	}

	@Override
	public void stop(ClassPathXmlApplicationContext context) {
		scheduledExecutor.shutdown();

	}

}
