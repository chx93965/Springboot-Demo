package LayeredArchitectureDemo.controller;

import LayeredArchitectureDemo.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import LayeredArchitectureDemo.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * unit testing {@link MessageController}
 */
@WebMvcTest(MessageController.class)
@AutoConfigureWebClient
public class MessageControllerTest {

    @MockBean
    private MessageService msgService;

    private String url;

    private Message message;

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void reset(){
        url = "";
        message = new Message();
        message.setId(1L);
        message.setData(new ArrayList<>());
        message.setInfo("");
    }

    /**
     * test {@link MessageController#getMessage()}
     */
    @Test
    public void getMessage() throws Exception {

        List<Message> messageList = new ArrayList<>(Arrays.asList(message));
        doReturn(messageList).when(msgService).getMessage();

        url = "http://localhost:8085/msg";
        MockHttpServletResponse getTaskResponse = mvc.perform(get(url))
            .andExpect(status().isOk()).andReturn().getResponse();

        String expected = objMapper.writeValueAsString(messageList);
        String actual = getTaskResponse.getContentAsString();
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * test {@link MessageController#getMessageById(long)}
     */
    @Test
    public void getMessageById() throws Exception {

        doReturn(message).when(msgService).getMessageById(1L);

        url = "http://localhost:8085/msg/1";
        MockHttpServletResponse getMessageResponse = mvc.perform(get(url))
            .andExpect(status().isOk()).andReturn().getResponse();

        String expected = objMapper.writeValueAsString(message);
        String actual = getMessageResponse.getContentAsString();
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * test {@link MessageController#postMessage(Message)}
     */
    @Test
    public void postMessage() throws Exception {

        url = "http://localhost:8085/msg";
        RequestBuilder request = post(url)
            .content(objMapper.writeValueAsString(message))
            .contentType("application/json;charset=UTF-8");

        doNothing().when(msgService).postMessage(any(Message.class));
        mvc.perform(request).andExpect(status().isCreated());
    }

    /**
     * test {@link MessageController#putMessage(Set)}
     */
    @Test
    public void putMessage() throws Exception {

        doNothing().when(msgService).putMessage(new HashSet<>());

        url = "http://localhost:8085/msg";
        RequestBuilder request = put(url)
            .content(objMapper.writeValueAsString(new HashSet<>()))
            .contentType("application/json;charset=UTF-8");
        mvc.perform(request).andExpect(status().isOk());
    }

    /**
     * test {@link MessageController#deleteMessage(long)}
     */
    @Test
    public void deleteMessage() throws Exception {

        doNothing().when(msgService).deleteMessage(1L);

        url = "http://localhost:8085/msg/1";
        mvc.perform(delete(url)).andExpect(status().isOk());
        verify(msgService, times(1)).deleteMessage(1L);
    }

    /**
     * test {@link MessageController#clearMessage()}
     */
    @Test
    public void clearMessage() throws Exception {

        url = "http://localhost:8085/msg";

        doNothing().when(msgService).clearMessage();
        mvc.perform(delete(url)).andExpect(status().isOk());
        verify(msgService, times(1)).clearMessage();
    }
}
