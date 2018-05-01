package service.impl;

import org.junit.Test;

public class BoardServicesImplTest {

	public BoardServicesImplTest() {
		
	}
	
	@Test
	public void testCreateContent() {
		BoardServicesImpl.getInstance().createContent();
	}

}
