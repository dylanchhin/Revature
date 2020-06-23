package bank.model;

/***
 * This class is a model for the username and bank accountID pair that represents an association between a user and bank account in the sql database.
 *
 * @author Shawyn Kane
 */
public class UserNameBankAccountIDPair {
    private int accountID = -1;
    private String email;

    /***
     *
     * @param accountID
     * @param email
     */
    public UserNameBankAccountIDPair(int accountID, String email) {
        this.accountID = accountID;
        this.email = email;
    }

    /***
     *
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /***
     *
     * @return userName
     */
    public String getCustomerID() {
        return email;
    }

    /***
     *
     * @param pair
     * @return
     */
    public boolean equals(UserNameBankAccountIDPair pair) {
        return (this.accountID == pair.getAccountID() && this.email.equals(pair.email));
    }
}
