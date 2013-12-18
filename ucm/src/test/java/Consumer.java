import org.springframework.context.support.ClassPathXmlApplicationContext;

import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.profile.entity.UserPwd;
import fengfei.ucm.service.PingService;
import fengfei.ucm.service.UserService;

public class Consumer {

	/**
	 * @param args
	 * @throws DataAccessException
	 */
	public static void main(String[] args) throws DataAccessException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "consumer.xml" });
		context.start();
		PingService ping = (PingService) context.getBean("ping");
		System.out.println(ping.ping());
		UserService us = (UserService) context.getBean("userService"); // 获取远程服务代理
		for (int i = 0; i < 100; i++) {
			UserPwd userPwd = new UserPwd("userName" + i, "email" + i,
					"password");
			int u = us.saveUserPwd(userPwd);
			System.out.println(u);
		}

	}

}
