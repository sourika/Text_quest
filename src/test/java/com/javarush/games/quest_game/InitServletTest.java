package com.javarush.games.quest_game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;


public class InitServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext context;
    private RequestDispatcher dispatcher;
    private RequestDispatcher dispatcherRead;
    private InitServlet initServlet;

    @BeforeEach
    public void setUp() throws ServletException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        context = mock(ServletContext.class);
        dispatcher = mock(RequestDispatcher.class);
        dispatcherRead = mock(RequestDispatcher.class);
        ServletConfig config = mock(ServletConfig.class);
        when(config.getServletContext()).thenReturn(context);
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher("/prolog.jsp")).thenReturn(dispatcher);
        initServlet = new InitServlet();
        initServlet.init(config);
    }


    @ParameterizedTest
    @ValueSource(strings = {"Саша", "Ваня145", "Ira", "Anna589"})
    public void testCheckValidPlayerName(String playerName) throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn(playerName);
        boolean result = initServlet.checkPlayerName(playerName, request, response);
        Assertions.assertFalse(result);
    }

    @Test
    public void testCheckPlayerNameIsNull() throws ServletException, IOException {
        boolean result = initServlet.checkPlayerName(null, request, response);
        verify(request).setAttribute("error", "Пожалуйста, введите свое имя.");
        verify(dispatcher).forward(request, response);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test!", "@Никита", "peter_ms"})
    public void testCheckInvalidPlayerName_SpecialChars(String playerName) throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn(playerName);
        boolean result = initServlet.checkPlayerName(playerName, request, response);
        verify(request).setAttribute("error", "Пожалуйста, введите имя, состоящее только из букв и цифр.");
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testCheckInvalidPlayerName_Empty(String playerName) throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn(playerName);
        boolean result = initServlet.checkPlayerName(playerName, request, response);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDoGet_ValidPlayerName() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        when(context.getRequestDispatcher(("/read"))).thenReturn(dispatcherRead);
        when(request.getParameter("playerName")).thenReturn("validPlayerName");
        initServlet.doGet(request, response);
        verify(session, times(1)).setAttribute("playerName", "validPlayerName");
        verify(dispatcherRead, times(1)).forward(request, response);
    }

    @Test
    public void testDoGet_InvalidPlayerName() throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn("#invalidPlayerName");
        initServlet.doGet(request, response);
        verify(session, never()).setAttribute("playerName", "#invalidPlayerName");
        verify(dispatcherRead, never()).forward(request, response);
    }
}
