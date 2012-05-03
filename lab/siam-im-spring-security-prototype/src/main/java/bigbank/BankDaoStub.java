package bigbank;

import java.util.HashMap;
import java.util.Map;

public class BankDaoStub implements BankDao {
  private long id = 0;
  private Map<Long, Person> accounts = new HashMap<Long, Person>();

  public void createOrUpdateAccount(Person account) {
    if (account.getId() == -1) {
      id++;
      account.setId(id);
    }
    accounts.put(new Long(account.getId()), account);
    System.out.println("SAVE: " + account);
  }

  public Person[] findAccounts() {
    Person[] a = accounts.values().toArray(new Person[] {});
    System.out.println("Returning " + a.length + " account(s):");
    for (int i = 0; i < a.length; i++) {
      System.out.println(" > " + a[i]);
    }
    return a;
  }

  public Person readAccount(Long id) {
    return accounts.get(id);
  }

}
