package fengfei.ucm.server;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ContainerChain chain = new ContainerChain();
		chain.add(new ScheduledChain());
		chain.add(new RouterChain());
		try {
			chain.init();
			chain.start();
		} catch (Exception e) {
			e.printStackTrace();
			chain.stop();
		}
	}

}
