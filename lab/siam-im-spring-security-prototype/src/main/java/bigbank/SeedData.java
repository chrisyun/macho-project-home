package bigbank;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SeedData implements InitializingBean{
    private BankDao bankDao;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(bankDao);
        bankDao.createOrUpdateAccount(new Person("rod"));
        bankDao.createOrUpdateAccount(new Person("dianne"));
        bankDao.createOrUpdateAccount(new Person("scott"));
        bankDao.createOrUpdateAccount(new Person("peter"));
    }

    public void setBankDao(BankDao bankDao) {
        this.bankDao = bankDao;
    }

}
