package LayeredArchitectureDemo.service;

import LayeredArchitectureDemo.entity.Message;
import LayeredArchitectureDemo.exception.MessageException;
import LayeredArchitectureDemo.registry.MessageLib;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * unit testing {@link MessageService}
 */
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    MessageService msgService;

    @Mock
    private MessageLib messageLib;

    private Message message;

    private ObjectMapper objMapper = new ObjectMapper();

    @BeforeEach
    public void reset(){
        message = new Message();
        message.setId(1L);
    }

    /**
     * test {@link MessageService#getMessage()}
     */
    @Test
    public void getMessage() throws JsonProcessingException {

        HashMap<Long, Object> library = new HashMap<>();
        library.put(1L, message);
        doReturn(library).when(messageLib).getLibrary();

        List<Message> messageListActual = msgService.getMessage();
        List<Message> messageListExpected = new ArrayList<>();
        messageListExpected.add(message);

        verify(messageLib, times(1)).getLibrary();
        String expected = objMapper.writeValueAsString(messageListExpected);
        String actual = objMapper.writeValueAsString(messageListActual);
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * test {@link MessageService#getMessageById(long)}
     */
    @Test
    public void getMessageById(){

        doReturn(message).when(messageLib).get(1L);
        doReturn(null).when(messageLib).get(2L);

        msgService.getMessageById(1L);
        verify(messageLib, times(1)).get(1L);

        try{
            msgService.getMessageById(2L);
        }catch (MessageException e){
            System.out.println(e);
        }
        verify(messageLib, times(1)).get(2L);
    }

    /**
     * test {@link MessageService#postMessage(Message)}
     */
    @Test
    public void postMessage(){

        doReturn(false).when(messageLib).contains(1L);
        doReturn(true).when(messageLib).contains(2L);
        doNothing().when(messageLib).put(message);

        msgService.postMessage(message);
        verify(messageLib, times(1)).contains(1L);
        verify(messageLib, times(1)).put(message);

        message.setId(2L);
        try{
            msgService.postMessage(message);
        }catch (MessageException e){
            System.out.println(e);
        }
        verify(messageLib, times(1)).contains(2L);
    }

    /**
     * test {@link MessageService#putMessage(Set)}
     */
    @Test
    public void putMessage(){

        doReturn(false).when(messageLib).contains(1L);
        doReturn(true).when(messageLib).contains(2L);

        doNothing().when(messageLib).put(any(Message.class));
        Set<Message> messages = new HashSet<>();

        messages.add(message);
        Message existingMessage = new Message();
        existingMessage.setId(2L);
        messages.add(existingMessage);
        msgService.putMessage(messages);

        verify(messageLib, times(1)).contains(1L);
        verify(messageLib, times(1)).contains(2L);
        verify(messageLib, times(1)).put(message);
        verify(messageLib, times(1)).put(existingMessage);
    }

    /**
     * test {@link MessageService#deleteMessage(long)}
     */
    @Test
    public void deleteMessage(){

        doReturn(message).when(messageLib).get(1L);
        doReturn(null).when(messageLib).get(2L);
        doNothing().when(messageLib).remove(1L);

        msgService.deleteMessage(1L);
        verify(messageLib, times(1)).get(1L);
        verify(messageLib, times(1)).remove(1L);

        try{
            msgService.deleteMessage(2L);
        }catch (MessageException e){
            System.out.println(e);
        }
        verify(messageLib, times(1)).get(2L);
    }

    /**
     * test {@link MessageService#clearMessage()}
     */
    @Test
    public void clearMessages(){

        doNothing().when(messageLib).clear();

        msgService.clearMessage();
        verify(messageLib, times(1)).clear();
    }

}
