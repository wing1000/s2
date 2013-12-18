package fengfei.ucm.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PingServiceTest extends AbstractServiceTest {
	static PingService service;

	@Before
	public void setup() {
		service = (PingService) consumer.getBean("ping");
	}

	@Test
	public void testPing() {
		String msg = "ping test";
		String pong = service.ping();
		assertEquals(msg, "pong", pong);
	}

	@Test
	public void testPingString() {
		String msg = "ping test";
		String pong = service.ping("ping");
		assertEquals(msg, "pong", pong);
	}

	@Test
	public void testPingStringForException() {
		String msg = "ping test for exception";
		try {
			String pong = service.ping("NothingCommand");
			assertTrue(msg, false);
		} catch (Exception e) {
			assertTrue(msg, true);
			e.printStackTrace();

		}
	}

}
