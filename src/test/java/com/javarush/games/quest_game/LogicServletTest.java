package com.javarush.games.quest_game;

import org.junit.jupiter.api.Assertions;
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

public class LogicServletTest {

    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private LogicServlet logicServlet;


    @BeforeEach
    public void setUp() throws ServletException {
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        dispatcher = mock(RequestDispatcher.class);
        ServletConfig config = mock(ServletConfig.class);
        when(request.getSession()).thenReturn(session);
        when(config.getServletContext()).thenReturn(context);
        when(request.getServletContext()).thenReturn(context);
        when(request.getServletContext().getRequestDispatcher(anyString())).thenReturn(dispatcher);
        logicServlet = spy(new LogicServlet());
        logicServlet.init(config);
    }

    @Test
    public void testDoGet_UserAnswerIsNull() throws ServletException, IOException {
        when(request.getParameter("answer")).thenReturn(null);
        logicServlet.doGet(request, response);
        verify(request, times(1)).setAttribute("error", "Пожалуйста, выберите один вариант ответа.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_ContinueGame() throws ServletException, IOException {
        when(request.getParameter("answer")).thenReturn("userAnswerKey");
        doReturn("answer").when(logicServlet).extractAttribute(session, "userAnswerKey", String.class, request, response);
        doReturn("answer").when(logicServlet).extractAttribute(session, "correctAnswer", String.class, request, response);
        doReturn(3).when(logicServlet).extractAttribute(session, "count", Integer.class, request, response);
        doReturn(5).when(logicServlet).extractAttribute(session, "maxQuestionNumber", Integer.class, request, response);
        logicServlet.doGet(request, response);
        verify(request).setAttribute("isGuess", true);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_EndGame_Victory() throws ServletException, IOException {
        when(request.getParameter("answer")).thenReturn("userAnswerKey");
        doReturn("answer").when(logicServlet).extractAttribute(session, "userAnswerKey", String.class, request, response);
        doReturn("answer").when(logicServlet).extractAttribute(session, "correctAnswer", String.class, request, response);
        doReturn(5).when(logicServlet).extractAttribute(session, "count", Integer.class, request, response);
        doReturn(5).when(logicServlet).extractAttribute(session, "maxQuestionNumber", Integer.class, request, response);
        logicServlet.doGet(request, response);
        verify(request).setAttribute("isGuess", true);
        verify(request).setAttribute("isWin", true);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_EndGame_Failure() throws ServletException, IOException {
        when(request.getParameter("answer")).thenReturn("userAnswerKey");
        doReturn("wrongAnswer").when(logicServlet).extractAttribute(session, "userAnswerKey", String.class, request, response);
        doReturn("answer").when(logicServlet).extractAttribute(session, "correctAnswer", String.class, request, response);
        doReturn(3).when(logicServlet).extractAttribute(session, "count", Integer.class, request, response);
        doReturn(5).when(logicServlet).extractAttribute(session, "maxQuestionNumber", Integer.class, request, response);
        logicServlet.doGet(request, response);
        verify(request).setAttribute("isGuess", false);
        verify(request).setAttribute("isWin", false);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testExtractAttribute_AttributeIsNull() throws IOException, ServletException {
        when(session.getAttribute("testParameter")).thenReturn(null);
        when(request.getContextPath()).thenReturn("quest_game_war");
        String result = logicServlet.extractAttribute(session, "testParameter", String.class, request, response);
        verify(session, times(1)).invalidate();
        verify(response).sendRedirect("quest_game_war" + "/error.jsp");
        Assertions.assertNull(result);
    }

    @Test
    public void testExtractAttribute_WithWrongType() throws IOException, ServletException {
        when(session.getAttribute("testParameter")).thenReturn(123);
        when(request.getContextPath()).thenReturn("quest_game_war");
        String result = logicServlet.extractAttribute(session, "testParameter", String.class, request, response);
        verify(session, times(1)).invalidate();
        verify(response).sendRedirect("quest_game_war" + "/error.jsp");
        Assertions.assertNull(result);
    }

    @Test
    public void testExtractAttribute_WithCorrectType() throws IOException, ServletException {
        when(session.getAttribute("testParameter")).thenReturn("correctValue");
        String result = logicServlet.extractAttribute(session, "testParameter", String.class, request, response);
        Assertions.assertEquals("correctValue", result);
        verify(session, never()).invalidate();
        verify(response, never()).sendRedirect("quest_game_war" + "/error.jsp");
    }
}
