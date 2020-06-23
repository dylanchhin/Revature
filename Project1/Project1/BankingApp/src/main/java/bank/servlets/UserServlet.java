package bank.servlets;

import bank.model.User;
import bank.services.UserServices;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserServlet extends HttpServlet {
    UserServices us;

    @Override
    public void init() throws ServletException {
        super.init();
        us = new UserServices();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getSession().getAttribute("userEmail").toString();
        ArrayList<User> userList;
        if (us.retrieveUserByEmail(email).getRole().equals("admin")) {
            userList = us.getAllUsers();
            JSONArray jary = new JSONArray(userList);
            System.out.println(jary.toString());
            resp.getWriter().write(jary.toString());
        } else resp.setStatus(206);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getSession().getAttribute("userEmail").toString();
        PrintWriter out = resp.getWriter();
        System.out.println(req.getParameter("email") + req.getParameter("firstName") + req.getParameter("lastName") + req.getParameter("password") + req.getParameter("phoneNumber") + req.getParameter("role"));
        if (us.retrieveUserByEmail(email).getRole().equals("admin")) {
            if(req.getParameter("action").equals("create"))
            {
                User newUser = new User(req.getParameter("email"), req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("password"), req.getParameter("phoneNumber"), req.getParameter("role"));
                if (us.createUser(newUser)) resp.setStatus(201);
                else resp.setStatus(206);
            }
            else if(req.getParameter("action").equals("delete"))
            {
                User newUser = new User(req.getParameter("email"), "", "", "", "", "");
                if (us.deleteUser(newUser)) resp.setStatus(201);
                else resp.setStatus(206);
            }
            else if(req.getParameter("action").equals("update"))
            {
                User newUser = new User(req.getParameter("email"), req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("password"), req.getParameter("phoneNumber"), req.getParameter("role"));
                if (us.updateUser(newUser)){ resp.setStatus(201); out.write("Transaction Passed");}
                else {resp.setStatus(206); out.write("Transaction Passed");}
            }
        } else resp.setStatus(206);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getSession().getAttribute("userEmail").toString();
        if (us.retrieveUserByEmail(email).getRole().equals("admin")) {
            User newUser = new User(req.getParameter("email"), req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("password"), req.getParameter("phoneNumber"), req.getParameter("role"));
            if (us.updateUser(newUser)) resp.setStatus(201);
            else resp.setStatus(206);
        } else resp.setStatus(206);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getSession().getAttribute("userEmail").toString();
        if (us.retrieveUserByEmail(email).getRole().equals("admin")) {
            User newUser = new User(req.getParameter("email"), "", "", "", "", "");
            if (us.deleteUser(newUser)) resp.setStatus(201);
            else resp.setStatus(206);
        } else resp.setStatus(206);
    }
}
