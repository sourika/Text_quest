package com.javarush.games.quest_game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("playerName");
        if (checkPlayerName(playerName, req, resp)) {
            return;
        }
        HttpSession currentSession = req.getSession(true);
        currentSession.setAttribute("playerName", playerName);
        String ipAddress = req.getRemoteAddr();
        currentSession.setAttribute("ipAddress", ipAddress);
        String sessionId = req.getSession().getId();
        currentSession.setAttribute("sessionId", sessionId);
        int gameNumber = 1;
        currentSession.setAttribute("gameNumber", gameNumber);
        getServletContext().getRequestDispatcher("/read").forward(req, resp);
    }

    boolean checkPlayerName(String playerName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (playerName == null || playerName.trim().isEmpty()) {
            request.setAttribute("error", "Пожалуйста, введите свое имя.");
            getServletContext().getRequestDispatcher("/prolog.jsp").forward(request, response);
            return true;
        }
        if (!playerName.matches("^[а-яА-Яa-zA-Z0-9]+$")) {
            request.setAttribute("error", "Пожалуйста, введите имя, состоящее только из букв и цифр.");
            getServletContext().getRequestDispatcher("/prolog.jsp").forward(request, response);
            return true;
        }
        return false;
    }
}
