package fengfei.ucm.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Ignore
public class AbstractServiceTest {
	protected static ClassPathXmlApplicationContext provider = null;
	protected static ClassPathXmlApplicationContext consumer = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		provider = new ClassPathXmlApplicationContext(new String[] {
				"service.xml", "provider.xml" });

		provider.start();

		consumer = new ClassPathXmlApplicationContext(
				new String[] { "consumer.xml" });
		consumer.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		provider.stop();
		consumer.stop();
	}

}
