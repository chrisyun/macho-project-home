package bigbank;

import org.springframework.security.access.prepost.PreAuthorize;

public interface PersonService {

  public Person readAccount(Long id);

  //@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
  public Person[] findAccounts();

  // @PreAuthorize("hasRole('supervisor') or hasRole('teller') and (#account.balance + #amount >= -#account.overdraft)")
  //@PreAuthorize("hasRole('spOrgUnitAccountManagerRole') and hasPermission(new Object[]{#person, #person}, 'person.create')")
  @PreAuthorize("hasRole('spOrgUnitAccountManagerRole') and hasPermission(#person, 'person.create')")
  //@PreAuthorize("hasRole('spOrgUnitAccountManagerRole') and authentication.isPersonManager(#person)")
  public Person post(Person person, double amount);
}
