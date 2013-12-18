package fengfei.ucm.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {

	private static final Logger logger = LoggerFactory
			.getLogger(ServerMain.class);

	public static void main(String args[]) {

		final ScheduledExecutorService scheduledExecutor = Executors
				.newScheduledThreadPool(1);
		scheduledExecutor.schedule(new Runnable() {

			@Override
			public void run() {
				logger.info("for daemon threads.");

			}
		}, 1, TimeUnit.DAYS);

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "service.xml", "provider.xml" });

		try {
			context.start();
		} catch (Throwable e) {
			e.printStackTrace();
			// scheduledExecutor.shutdown();
			// relationServer.shutdown();
			scheduledExecutor.shutdown();
			System.exit(1);
		}

	}
}
