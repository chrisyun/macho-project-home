package bigbank;

public interface BankDao {
    public Person readAccount(Long id);
    public void createOrUpdateAccount(Person account);
    public Person[] findAccounts();
}
