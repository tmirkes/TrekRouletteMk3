package controller;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.TrekDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(
        urlPatterns = {"/loadUser"}
)
public class UserLoader extends HttpServlet {
    private TrekDao userDao = new TrekDao(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/member.jsp");
        dispatcher.forward(req, resp);
    }

    public void checkUserExistence(String userName) {
        List<User> userMatches = new ArrayList<User>(userDao.getByPropertyEqual("user_name",userName));
        if (userMatches.size() > 0) {

        } else {
            addNewUser();
        }
    }

    public void addNewUser() {
    }
}
