package fengfei.ucm.service;

import fengfei.ucm.repository.UserRepository;

public interface UserService extends UserRepository {
	String sayHello(String name);

}
