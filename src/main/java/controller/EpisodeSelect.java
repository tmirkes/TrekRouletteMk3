package controller;

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import entity.Episode;
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
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet(name = "EpisodeSelect", urlPatterns = "/getEpisode")
public class EpisodeSelect extends HttpServlet {
    private FetchEpisodeId fetcher = new FetchEpisodeId();
    private String episodeId = "";
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ArrayList<String> episodeList = (ArrayList<String>) session.getAttribute("episodeList");
        ArrayList<Integer> randomList = (ArrayList<Integer>) session.getAttribute("randomList");
        episodeId = fetcher.selectRandomEpisodeId(episodeList, randomList);
        randomList.remove(0);
        session.setAttribute("randomList", randomList);

        ApiEpisodeLoader episodeRetriever = new ApiEpisodeLoader();
        EpisodeFullResponse selection = episodeRetriever.getEpisodeData(episodeId);
        session.setAttribute("selection", selection);

        String url = "";
        if (randomList.size() == 1) {
            url = "/snapshot";
        } else if (session.getAttribute("currentUser") == null || session.getAttribute("currentUser").equals("")) {
            url = "/public.jsp";
        } else {
            url = "/member.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
