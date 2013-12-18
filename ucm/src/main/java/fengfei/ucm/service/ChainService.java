package fengfei.ucm.service;

import java.util.ArrayList;

public interface ChainService {
	<T> T execute(Object[] args, ArrayList<Object> results) throws Exception;
}
