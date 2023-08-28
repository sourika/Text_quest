package com.javarush.games.quest_game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GameControlServlet", value = "/game-control")
public class GameControlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        String action = req.getParameter("action");
        if ("restart".equals(action)) {
            Object gameNumberAttribute = currentSession.getAttribute("gameNumber");
            if (!(gameNumberAttribute instanceof Integer)) {
                currentSession.invalidate();
                resp.sendRedirect(req.getContextPath() + "/error.jsp");
                return;
            }
            int gameNumber = (int) (gameNumberAttribute) + 1;
            currentSession.setAttribute("gameNumber", gameNumber);

            currentSession.setAttribute("count", 0);
            getServletContext().getRequestDispatcher("/read").forward(req, resp);
        }
        if ("quit".equals(action)) {
            currentSession.invalidate();
            resp.sendRedirect(req.getContextPath() + "/prolog.jsp");
        }
    }
}
