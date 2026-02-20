package LayeredArchitectureDemo.controller;

import LayeredArchitectureDemo.entity.Message;
import LayeredArchitectureDemo.entity.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import LayeredArchitectureDemo.service.IMessageService;
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
    private IMessageService msgService;

    private String url;

    private MessageDto messageDto;

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void reset(){
        url = "";
        messageDto = new MessageDto();
        messageDto.setId(1L);
        messageDto.setData(new ArrayList<>());
        messageDto.setInfo("");
    }

    /**
     * test {@link MessageController#getMessage()}
     */
    @Test
    public void getMessage() throws Exception {

        List<MessageDto> messageList = new ArrayList<>(Arrays.asList(messageDto));
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

        doReturn(messageDto).when(msgService).getMessageById(1L);

        url = "http://localhost:8085/msg/1";
        MockHttpServletResponse getMessageResponse = mvc.perform(get(url))
            .andExpect(status().isOk()).andReturn().getResponse();

        String expected = objMapper.writeValueAsString(messageDto);
        String actual = getMessageResponse.getContentAsString();
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * test {@link MessageController#postMessage(MessageDto)}
     */
    @Test
    public void postMessage() throws Exception {

        url = "http://localhost:8085/msg";
        RequestBuilder request = post(url)
            .content(objMapper.writeValueAsString(messageDto))
            .contentType("application/json;charset=UTF-8");

        doNothing().when(msgService).postMessage(any(MessageDto.class));
        mvc.perform(request).andExpect(status().isCreated());
    }

    /**
     * test {@link MessageController#putMessage(long, MessageDto)}
     */
    @Test
    public void putMessage() throws Exception {

        doNothing().when(msgService).putMessage(1L, messageDto);

        url = "http://localhost:8085/msg/1";
        RequestBuilder request = put(url)
            .content(objMapper.writeValueAsString(messageDto))
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
