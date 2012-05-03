package bigbank;

import org.springframework.util.Assert;

public class PersonServiceImpl implements PersonService {
  private BankDao bankDao;

  public PersonServiceImpl(BankDao bankDao) {
    Assert.notNull(bankDao);
    this.bankDao = bankDao;
  }

  public Person[] findAccounts() {
    return this.bankDao.findAccounts();
  }

  public Person post(Person person, double amount) {
    Assert.notNull(person);

    // We read account bank from DAO so it reflects the latest balance
    Person a = bankDao.readAccount(person.getId());
    if (person == null) {
      throw new IllegalArgumentException("Couldn't find requested account");
    }

    a.setBalance(a.getBalance() + amount);
    bankDao.createOrUpdateAccount(a);
    return a;
  }

  public Person readAccount(Long id) {
    return bankDao.readAccount(id);
  }
}
