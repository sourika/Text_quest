package com.javarush.games.quest_game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class PropertiesReaderServletTest {

    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Properties properties;
    private PropertiesReaderServlet propertiesReaderServlet;

    @BeforeEach
    public void setUp() {
        propertiesReaderServlet = new PropertiesReaderServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        properties = mock(Properties.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void testLoadConfigProperties_PropertiesIsEmpty() throws IOException {
        propertiesReaderServlet.setProp(properties);
        when(properties.isEmpty()).thenReturn(true);
        when(request.getContextPath()).thenReturn("quest_game_war");
        boolean result = propertiesReaderServlet.loadConfigProperties(session, request, response);
        verify(session, times(1)).invalidate();
        verify(response).sendRedirect("quest_game_war" + "/error.jsp");
        Assertions.assertTrue(result);
    }

    @Test
    public void testLoadConfigProperties_PropertiesIsNotEmpty() throws IOException {
        propertiesReaderServlet.setProp(properties);
        when(properties.isEmpty()).thenReturn(false);
        boolean result = propertiesReaderServlet.loadConfigProperties(session, request, response);
        verify(session, never()).invalidate();
        verify(response, never()).sendRedirect(anyString());
        Assertions.assertFalse(result);
    }

    @Test
    public void testGetCount_CountIsNotNull() {
        when(session.getAttribute("count")).thenReturn(4);
        int result = propertiesReaderServlet.getCount(session);
        Assertions.assertEquals(5, result);
    }

    @Test
    public void testGetCount_CountIsNull() {
        when(session.getAttribute("count")).thenReturn(null);
        int result = propertiesReaderServlet.getCount(session);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testGetMaxQuestionNumber() {
        properties = new Properties();
        properties.setProperty("question1", "How?");
        properties.setProperty("question2", "Where?");
        properties.setProperty("answer8", "I am");
        properties.setProperty("question10", "What?");
        int result = propertiesReaderServlet.getMaxQuestionNumber(properties);
        Assertions.assertEquals(3, result);
    }
}
