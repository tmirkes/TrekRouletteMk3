package controller;

import com.cezarykluczynski.stapi.client.api.rest.Episode;
import com.cezarykluczynski.stapi.client.v1.rest.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "EpisodeSelect", urlPatterns = "/selector")
public class EpisodeSelect extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Episode episode = (Episode)session.getAttribute("Episodes");
        StapiParse parser = new StapiParse(episode);
        EpisodeFullResponse selection = parser.recommendEpisode();
        session.setAttribute("selection", selection);
        String url = "/roulette.jsp";
        //response.sendRedirect(request.getContextPath() + url);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
