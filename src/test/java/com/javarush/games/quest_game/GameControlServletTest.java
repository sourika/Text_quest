package com.javarush.games.quest_game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class GameControlServletTest {

    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private GameControlServlet gameControlServlet;

    @BeforeEach
    public void setUp() throws ServletException {
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        dispatcher = mock(RequestDispatcher.class);
        ServletConfig config = mock(ServletConfig.class);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(config.getServletContext()).thenReturn(context);
        gameControlServlet = new GameControlServlet();
        gameControlServlet.init(config);
    }

    @Test
    public void testDoGet_RestartAction_WithGameNumberAttribute() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("restart");
        when(session.getAttribute("gameNumber")).thenReturn(4);
        gameControlServlet.doGet(request, response);
        verify(session).setAttribute("gameNumber", 5);
        verify(session).setAttribute("count", 0);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_RestartAction_NoGameNumberAttribute() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("restart");
        when(session.getAttribute("gameNumber")).thenReturn(null);
        when(request.getContextPath()).thenReturn("quest_game_war");
        gameControlServlet.doGet(request, response);
        verify(session).invalidate();
        verify(response).sendRedirect("quest_game_war" + "/error.jsp");
    }

    @Test
    public void testDoGet_QuitAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("quit");
        when(request.getContextPath()).thenReturn("quest_game_war");
        gameControlServlet.doGet(request, response);
        verify(session).invalidate();
        verify(response).sendRedirect("quest_game_war" + "/prolog.jsp");
    }
}
