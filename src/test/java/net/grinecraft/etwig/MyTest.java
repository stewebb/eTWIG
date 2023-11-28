package net.grinecraft.etwig;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import junit.framework.Assert;
import net.grinecraft.etwig.DAO.EventService;
import net.grinecraft.etwig.Model.Events;

@SuppressWarnings("deprecation")
class MyTest {

	@ExtendWith(SpringExtension.class)
	@SpringBootTest
	public class BookServiceUnitTest {

	    @Autowired
	    private EventService eventService;

	    @Test
	    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
	        List books = (List) eventService.list();

	        Assert.assertEquals(books.size(), 3);
	    }
	}

}
