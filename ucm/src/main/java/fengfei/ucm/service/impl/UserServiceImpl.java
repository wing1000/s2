package fengfei.ucm.service.impl;

import fengfei.ucm.repository.impl.SqlUserRepository;
import fengfei.ucm.service.UserService;

public class UserServiceImpl extends SqlUserRepository implements UserService {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

}
