package com.javarush.games.quest_game;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

@WebServlet(name = "PropertiesReaderServlet", value = "/read")
public class PropertiesReaderServlet extends HttpServlet {
    private static final String PROP_FILE_NAME = "config.properties";
    private static final Pattern PATTERN = Pattern.compile("^question\\d+$");
    private Properties prop = new Properties();


    @Override
    public void init() throws ServletException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new ServletException("Couldn't load " + PROP_FILE_NAME);
            }
        } catch (IOException e) {
            throw new ServletException("Error loading " + PROP_FILE_NAME, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (loadConfigProperties(session, req, resp)) {
            return;
        }
        int count = getCount(session);
        session.setAttribute("count", count);

        String question = prop.getProperty("question" + count);
        String answer1 = prop.getProperty("answer" + count + "_1");
        String answer2 = prop.getProperty("answer" + count + "_2");
        String correctAnswer = prop.getProperty("correctAnswer" + count);

        session.setAttribute("question", question);
        session.setAttribute("answer1", answer1);
        session.setAttribute("answer2", answer2);
        session.setAttribute("correctAnswer", correctAnswer);

        int maxQuestionNumber = getMaxQuestionNumber(prop);
        session.setAttribute("maxQuestionNumber", maxQuestionNumber);

        getServletContext().getRequestDispatcher("/page.jsp").forward(req, resp);
    }

    boolean loadConfigProperties(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (prop.isEmpty()) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return true;
        }
    return false;
}

    int getCount(HttpSession session) {
        Object countAttribute = session.getAttribute("count");
        int count;
        if (countAttribute == null) {
            count = 1;
        } else {
            count = (int) countAttribute + 1;
        }
        return count;
    }

    int getMaxQuestionNumber(Properties prop) {
        Set<String> keys = prop.stringPropertyNames();
        return (int) keys.stream()
                .filter(key -> PATTERN.matcher(key).matches())
                .count();
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
}