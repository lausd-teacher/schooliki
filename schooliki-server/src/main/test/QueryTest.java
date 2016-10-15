import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.user.DB.db;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class QueryTest {
	
	 private final LocalServiceTestHelper helper =
			 new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
			            .setDefaultHighRepJobPolicyUnappliedJobPercentage(100).setBackingStoreLocation("src/main/webapp/WEB-INF/appengine-generated/local_db.bin"));

	 private DatastoreService datastore;
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		datastore = DatastoreServiceFactory.getDatastoreService();
		
		
	}

	@Test
	public void test() {
		

		Query q =
			    new Query("AppUser");
		
		//.setFilter(new FilterPredicate("idBis", FilterOperator.EQUAL, 4978588650569728L))

		PreparedQuery pq = datastore.prepare(q);
		
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		
		
		//assertEquals(list.size(), "Lee");
		
		assertNotEquals(list.size(), 0);
		
	}
	
	 @After
	  public void tearDown() {
	    helper.tearDown();
	  }

}
