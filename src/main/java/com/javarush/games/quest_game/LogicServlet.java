package com.javarush.games.quest_game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession currentSession = request.getSession();
        String userAnswerKey = request.getParameter("answer");
        if (userAnswerKey == null || userAnswerKey.trim().isEmpty()) {
            request.setAttribute("error", "Пожалуйста, выберите один вариант ответа.");
            getServletContext().getRequestDispatcher("/page.jsp").forward(request, response);
            return;
        }
        String userAnswer = extractAttribute(currentSession, userAnswerKey, String.class, request, response);
        if (userAnswer == null) {
            return;
        }
        String correctAnswer = extractAttribute(currentSession,"correctAnswer", String.class, request, response);
        if (correctAnswer == null) {
            return;
        }
        Integer count = extractAttribute(currentSession,"count", Integer.class, request, response);
        if (count == null) {
            return;
        }
        Integer maxQuestionNumber = extractAttribute(currentSession, "maxQuestionNumber", Integer.class, request, response);
        if (maxQuestionNumber == null) {
            return;
        }
        boolean isGuess = correctAnswer.equals(userAnswer);
        request.setAttribute("isGuess", isGuess);
        if (isGuess && count < maxQuestionNumber) {
            getServletContext().getRequestDispatcher("/read").forward(request, response);
        } else {
            boolean isWin = isGuess && count.equals(maxQuestionNumber);
            request.setAttribute("isWin", isWin);
            getServletContext().getRequestDispatcher("/end.jsp").forward(request, response);
        }
    }

    <T> T extractAttribute(HttpSession currentSession, String parameter, Class<T> expectedType, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object attribute = currentSession.getAttribute(parameter);
        if (attribute == null || (expectedType != attribute.getClass())) {
            currentSession.invalidate();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return null;
        }
        return expectedType.cast(attribute);
    }
}



