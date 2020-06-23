package bank.servlets;

import bank.model.Transaction;
import bank.services.AccountService;
import bank.services.UserServices;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TransferServlet extends HttpServlet {
    UserServices us;
    AccountService accountService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    /*
    * service -- runs for each and every request made, after the init method
                 has run at least once.
     */
        System.out.println("Servicing MyServlet");
        us = new UserServices();
        accountService = new AccountService();
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        /*
         * destroy -- gets called when the server needs it to be.
         *           most likely at server shutdown.
         * */
        System.out.println("Destroy MyServlet");
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        /*
         * init -- beginning of the servlet lifecycle
         *         it runs once, if the servlet has never been initialize
         *         when the first request to a matching url pattern is made.
         *         You can preload servlet with <load-on-startup> in the web.xml.
         * */
        System.out.println("Init MyServlet");
        super.init();
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject job=new JSONObject(); //create a JSON Object obj.
        JSONArray jary = new JSONArray();
        job.put("Transaction", "Dylan");
        jary.put(job);
        resp.getWriter().write(jary.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject job=new JSONObject();
        boolean transactionValid = false;
        String email = req.getSession().getAttribute("userEmail").toString();
        if (us.retrieveUserByEmail(email).getRole().equals("customer")) {
            try
            {
                transactionValid = accountService.transfer(email, Integer.parseInt(req.getParameter("accountid")), Double.parseDouble(req.getParameter("amount")), Integer.parseInt(req.getParameter("otherAccountID")));
            }catch(IllegalArgumentException e)
            {
                transactionValid = false;
            }
        }
        if(transactionValid)
        {
            resp.setStatus(201);
            PrintWriter out = resp.getWriter();
            out.write("Transaction Passed");
        }
        else
        {
            resp.setStatus(206);
            PrintWriter out = resp.getWriter();
            out.write("Transaction Failed");
        }
        resp.getWriter().write(job.toString());
    }
}
