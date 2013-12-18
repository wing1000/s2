package fengfei.ucm.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface ServerChain {
	void init(ClassPathXmlApplicationContext context);

	void start(ClassPathXmlApplicationContext context);

	void restart(ClassPathXmlApplicationContext context);

	void stop(ClassPathXmlApplicationContext context);
}
