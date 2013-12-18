package fengfei.ucm.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContainerChain {
	private static final Logger logger = LoggerFactory
			.getLogger(ContainerChain.class);
	List<ServerChain> chains = new ArrayList<>();
	ClassPathXmlApplicationContext context;

	public void init() {
		this.context = new ClassPathXmlApplicationContext(new String[] {
				"service.xml", "provider.xml" });
		context.start();
		//
		for (ServerChain chain : chains) {
			logger.info("Initailizing " + chain.getClass().getSimpleName());
			chain.init(this.context);
			logger.info("Initailized " + chain.getClass().getSimpleName());
		}

	}

	public void start() {

		for (ServerChain chain : chains) {
			logger.info("Starting " + chain.getClass().getSimpleName());
			chain.start(this.context);
			logger.info("Started " + chain.getClass().getSimpleName());
		}
	}

	public void restart() {
		for (ServerChain chain : chains) {
			logger.info("Restarting " + chain.getClass().getSimpleName());
			chain.restart(this.context);
			logger.info("Restarted " + chain.getClass().getSimpleName());
		}
	}

	public void stop() {

		for (ServerChain chain : chains) {
			logger.info("Stoping " + chain.getClass().getSimpleName());
			chain.stop(this.context);
			logger.info("Stoped " + chain.getClass().getSimpleName());
		}
		context.stop();
		logger.info("All ServerChains stoped. ");
	}

	public void add(ServerChain chain) {
		chains.add(chain);
	}

}
