package bank.servlets;

import bank.model.User;
import bank.model.UserNameBankAccountIDPair;
import bank.services.AccountService;
import bank.services.UserServices;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

public class UserProfileServlet extends HttpServlet {
    UserServices us;
    AccountService accountService;
    @Override
    public void init() throws ServletException {
        super.init();
        us = new UserServices();
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject job = new JSONObject();
        JSONArray jary = new JSONArray();
        String email = req.getSession().getAttribute("userEmail").toString();
        System.out.println(email);
        User user = us.retrieveUserByEmail(email);
        System.out.println(user.getEmail());
        UserNameBankAccountIDPair[] pairs = accountService.getAccounts(user);
        System.out.println(pairs.length);
        ArrayList<String> accountID = new ArrayList<String>();
        ArrayList<Double> accountBalance = new ArrayList<Double>();
        for(int x = 0; x < pairs.length; x++)
        {
            double balance = accountService.getBalance(email, pairs[x].getAccountID());
            accountID.add(pairs[x].getAccountID() + "");
            accountBalance.add(balance);
        }
        job.put("account", accountID);
        job.put("balance", accountBalance);
        job.put("firstname", user.getFirstName());
        job.put("lastname", user.getLastName());
        job.put("email", user.getEmail());
        job.put("phone", user.getPhoneNumber());
        jary.put(job);
        System.out.println(jary.toString());
        resp.getWriter().write(jary.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
