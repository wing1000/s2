package fengfei.ucm.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fengfei.forest.slice.config.Config;
import fengfei.forest.slice.config.SliceConfigReader;
import fengfei.forest.slice.config.xml.XmlSliceConfigReader;
import fengfei.forest.slice.database.DatabaseRouterFactory;
import fengfei.ucm.dao.Transactions;

public class RouterChain implements ServerChain {
	private static final Logger logger = LoggerFactory
			.getLogger(RouterChain.class);
	DatabaseRouterFactory databaseRouterFactory;
	SliceConfigReader configReader;

	@Override
	public void init(ClassPathXmlApplicationContext context) {

		configReader = new XmlSliceConfigReader("cp:config.xml");
		logger.info("reading xml config...");
		Config config = configReader.read("/root");
		logger.info("pasering config....");
		databaseRouterFactory = new DatabaseRouterFactory(config);
		logger.info("pasered config.");
	}

	@Override
	public void start(ClassPathXmlApplicationContext context) {
		Transactions.setDatabaseSliceGroupFactory(databaseRouterFactory);

	}

	@Override
	public void restart(ClassPathXmlApplicationContext context) {
		Transactions.databaseRouterFactory = null;
		init(context);
		start(context);
	}

	@Override
	public void stop(ClassPathXmlApplicationContext context) {

	}

}
