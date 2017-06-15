package contact.persistence;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class TestContactDAO {
	private ContactDAO dao;
	
	@Before
	public void setup() {
		dao	= new ContactDAO();
	}
	
	@Test
	public void testSearchAll() {
		List<Contact> personnes = dao.searchAll();
		Assert.assertNotNull(personnes);
	}

}
