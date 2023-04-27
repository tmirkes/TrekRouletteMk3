package controller;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(
        urlPatterns = {"/collect"}
)
public class UpdateCollection extends HttpServlet {
    private TrekDao<Own> ownDao = new TrekDao(Own.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] setCollection = req.getParameterValues("owned");
        List<Own> ownedSeasons = ownDao.getByPropertyEqual("user_id", req.getParameter("userId"));
        logger.info(setCollection);
        logger.info(ownedSeasons.toString());
    }
}
