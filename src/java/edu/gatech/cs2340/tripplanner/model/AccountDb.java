package java.edu.gatech.cs2340.tripplanner.model;

import java.util.Map;

/**
 * Created by Charlie on 6/5/2014.
 */
public interface AccountDb {

    public Integer create(Account acct);

    public Map<Integer, Account> list();

    public Account get(Integer id);

    public void update(Integer id, Account acct);
}
