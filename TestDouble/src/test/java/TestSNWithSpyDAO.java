import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestSNWithSpyDAO extends TestSNAbstractGeneric {
	
	@Override @Before
	public void setUp() throws Exception {
		// whatever you need to do here
		super.setUp();
	}
	
	/* 
	 * Generic tests are automatically inherited from abstract superclass - they should continue to work here! 
	 */
	
	/* 
	 * VERIFICATION TESTS
	 * 
	 * These tests use a spy (that wraps a real or fake object) and verify that persistence operations are called. 
	 * They ONLY ensure that the right persistence operations of the mocked IAccountDAO implementation are called with
	 * the right parameters. They need not and cannot verify that the underlying DB is actually updated. 
	 * They don't verify the state of the SocialNetwork either. 
	 * 
	 */
	
	@Test public void willAttemptToPersistANewAccount() throws UserExistsException {
		// make sure that when a new member account is created, it will be persisted 
		fail();
	}
	
	@Test public void willAttemptToPersistSendingAFriendRequest() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member issues a friend request, any changes to the affected accounts will be persisted
		fail();	}
	
	@Test public void willAttemptToPersistAcceptanceOfFriendRequest() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member issues a friend request, any changes to the affected accounts will be persisted
		fail();	}
	
	@Test public void willAttemptToPersistRejectionOfFriendRequest() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member rejects a friend request, any changes to the affected accounts will be persisted
		fail();	}
	
	@Test public void willAttemptToPersistBlockingAMember() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member blocks another member, any changes to the affected accounts will be persisted
		fail();	}
		
	@Test public void willAttemptToPersistLeavingSocialNetwork() throws UserExistsException, UserNotFoundException, NoUserLoggedInException {
		// make sure that when a logged-in member leaves the social network, his account will be permanenlty deleted and  
		// any changes to the affected accounts will be persisted
		fail();	
	}

}
