import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


public class TestSNWithMockDAO extends TestSNAbstractGeneric {

//    @Mock
//    AccountDAO accountDAO;
	
	@Override @Before
	public void setUp() throws Exception {
		// whatever you need to do here

		accountDAO = mock(IAccountDAO.class);
		sn = new SocialNetwork(accountDAO);
        super.setUp();

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Account acc = (Account)(invocationOnMock.getArguments()[0]);
                when(accountDAO.findByUserName("Hakan")).thenReturn(null);
                return null;
            }
        }).when(accountDAO).delete(any(Account.class));
	}


	/* 
	 * Generic tests are automatically inherited from abstract superclass - they should continue to work here! 
	 */
	
	/* 
	 * VERIFICATION TESTS
	 * 
	 * These tests use a mock and verify that persistence operations are called. 
	 * They ONLY ensure that the right persistence operations of the mocked IAccountDAO implementation are called with
	 * the right parameters. They need not and cannot verify that the underlying DB is actually updated. 
	 * They don't verify the state of the SocialNetwork either. 
	 * 
	 */
	
	@Test public void willAttemptToPersistANewAccount() throws UserExistsException {
		// make sure that when a new member account is created, it will be persisted

        when(accountDAO.findByUserName("Tom")).thenReturn(null);
        Account me = sn.join("Tom");

        verify(accountDAO, times(1)).save(me);
	}
	
	@Test public void willAttemptToPersistSendingAFriendRequest() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member issues a friend request, any changes to the affected accounts will be persisted

        when(accountDAO.findByUserName("Tom")).thenReturn(null);
        when(accountDAO.findByUserName("Cecile")).thenReturn(null);
        Account me = sn.join("Tom");
        when(accountDAO.findByUserName("Tom")).thenReturn(me);
        Account her = sn.join("Cecile");
        when(accountDAO.findByUserName("Cecile")).thenReturn(her);
        sn.login(me);
        sn.sendFriendshipTo("Cecile");
        verify(accountDAO, times(1)).update(me);

	}
	
	@Test public void willAttemptToPersistAcceptanceOfFriendRequest() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member issues a friend request, any changes to the affected accounts will be persisted

        when(accountDAO.findByUserName("Tom")).thenReturn(null);
        when(accountDAO.findByUserName("Cecile")).thenReturn(null);
        Account me = sn.join("Tom");
        when(accountDAO.findByUserName("Tom")).thenReturn(me);
        Account her = sn.join("Cecile");
        when(accountDAO.findByUserName("Cecile")).thenReturn(her);
        sn.login(me);
        sn.sendFriendshipTo("Cecile");
        verify(accountDAO, times(1)).update(me);
	}
	
	@Test public void willAttemptToPersistRejectionOfFriendRequest() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member rejects a friend request, any changes to the affected accounts will be persisted
        when(accountDAO.findByUserName("Tom")).thenReturn(null);
        when(accountDAO.findByUserName("Cecile")).thenReturn(null);
        Account me = sn.join("Tom");
        when(accountDAO.findByUserName("Tom")).thenReturn(me);
        Account her = sn.join("Cecile");
        when(accountDAO.findByUserName("Cecile")).thenReturn(her);
        sn.login(me);
        sn.sendFriendshipTo("Cecile");
        sn.logout();
        sn.login(her);
        sn.rejectFriendshipFrom("Tom");
        verify(accountDAO, times(1)).update(me);
        verify(accountDAO, times(1)).update(her);
	}
	
	@Test public void willAttemptToPersistBlockingAMember() throws UserNotFoundException, UserExistsException, NoUserLoggedInException {
		// make sure that when a logged-in member blocks another member, any changes to the affected accounts will be persisted

        when(accountDAO.findByUserName("Tom")).thenReturn(null);
        when(accountDAO.findByUserName("Cecile")).thenReturn(null);
        Account me = sn.join("Tom");
        when(accountDAO.findByUserName("Tom")).thenReturn(me);
        Account her = sn.join("Cecile");
        when(accountDAO.findByUserName("Cecile")).thenReturn(her);
        sn.login(me);
        sn.block("Cecile");
        verify(accountDAO, times(1)).update(me);
        verify(accountDAO, times(1)).update(her);
	}
		
	@Test public void willAttemptToPersistLeavingSocialNetwork() throws UserExistsException, UserNotFoundException, NoUserLoggedInException {
		// make sure that when a logged-in member leaves the social network, his account will be permanenlty deleted and  
		// any changes to the affected accounts will be persisted

        when(accountDAO.findByUserName("Tom")).thenReturn(null);
        Account me = sn.join("Tom");
        when(accountDAO.findByUserName("Cecile")).thenReturn(null);
        Account her = sn.join("Cecile");
        when(accountDAO.findByUserName("Tom")).thenReturn(me);
        sn.login(me);
        when(accountDAO.findByUserName("Cecile")).thenReturn(her);
        sn.sendFriendshipTo("Cecile");
        sn.logout();
        sn.login(her);
        sn.acceptFriendshipFrom("Tom");
        sn.logout();
        sn.login(me);
        sn.leave();
        verify(accountDAO).delete(me);
        verify(accountDAO, times(2)).update(her);
	}



}
