package fengfei.performance.dubbo;

import java.util.Random;

import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.protocol.java.test.JavaTest;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fengfei.ucm.UserService;

public class Dubbo extends JavaTest {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Dubbo.class);
	Random random = new Random();

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sampleResult = new SampleResult();
		sampleResult.sampleStart();
		sampleResult.sampleEnd();
		boolean success = true;
		try {
			String hello = demoService.sayHello("name-" + random.nextInt());
		} catch (Exception e) {

			success = false;
			logger.error("test case exception", e);
		}
		sampleResult.setSuccessful(success);
		sampleResult.setResponseCodeOK();
		sampleResult.setResponseMessageOK();

		return sampleResult;

	}

	@Override
	public void setupTest(JavaSamplerContext context) {
		// source
		super.setupTest(context);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "consumer.xml" });
		applicationContext.start();
		System.out.println(applicationContext);
		demoService = (UserService) applicationContext.getBean("userService"); // 获取远程服务代理
		System.out.println(demoService);
	}

	UserService demoService;
}
