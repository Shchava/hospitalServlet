package ua.training.servlet.hospital.controller.command.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.servlet.hospital.controller.command.utilities.GetSessionAttribute.getUserId;

public class GetSessionAttributeTest {
    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    private User user = new User(20L);

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(user);
    }

    @Test
    public void testGetUserId(){
        assertEquals(20L, getUserId(request));
    }

}