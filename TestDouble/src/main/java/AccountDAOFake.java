import java.util.HashSet;
import java.util.Set;


public class AccountDAOFake implements IAccountDAO {
	/*
	 * A full in-memory fake of AccountDAO.
	 * 
	 * IMPORTANT NOTE:
	 * If you take advantage of the Account class for simulating an in-memory database,
	 * make sure that your storage representation relies on fully cloned objects, not just Account references passed.
	 * Tests should not be able to distinguish this object from a real DAO object connected to a real DB. 
	 * In this sense, this class is a full fake that works with all inputs. 
	 */

	Set<Account> memberSet = new HashSet<Account>();
		
	public boolean isFullFake() {  
		return true;
	}

	public void save(Account member) {
		// implement this method
		Account clone = member.clone();

		for (Account account : memberSet) {
			if (account.getUserName() == clone.getUserName()) {
				return;
			}
		}
		memberSet.add(clone);
	}

	public Account findByUserName(String userName) {
		// implement this method
		for (Account account : memberSet) {
			if (account.getUserName() == userName) {
				return account;
			}
		}
		//throw new UserNotFoundException(userName);
		return null;
	}
	
	public Set<Account> findAll()  {
		// implement this method
		return memberSet;
	}

	public void delete(Account member) {
		// implement this method
		Account account = findByUserName(member.getUserName());
		memberSet.remove(account);
	}

	public void update(Account member) {
		// implement this method
		Account account = findByUserName(member.getUserName());
		account = member.clone();
	}

}
