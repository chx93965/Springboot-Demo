package SpringbootDemo.service;

import SpringbootDemo.entity.Message;
import SpringbootDemo.entity.dto.MessageDto;
import SpringbootDemo.exception.MessageException;
import SpringbootDemo.repository.MessageRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

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
    private MessageRepo messageRepo;

    private Message message;

    private MessageDto messageDto;

    private ObjectMapper objMapper = new ObjectMapper();

    @BeforeEach
    public void reset(){
        message = new Message(1L, new ArrayList<>(), "");
        messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setData(message.getData());
        messageDto.setInfo(message.getInfo());
    }

    /**
     * test {@link MessageService#getMessage()}
     */
    @Test
    public void getMessage() throws JsonProcessingException {

        List<Message> messageList = new ArrayList<>(Arrays.asList(message));
        doReturn(messageList).when(messageRepo).findAll();

        List<MessageDto> messageListActual = msgService.getMessage();
        List<MessageDto> messageListExpected = new ArrayList<>(Arrays.asList(messageDto));

        verify(messageRepo, times(1)).findAll();
        String expected = objMapper.writeValueAsString(messageListExpected);
        String actual = objMapper.writeValueAsString(messageListActual);
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * test {@link MessageService#getMessageById(long)}
     */
    @Test
    public void getMessageById(){

        doReturn(Optional.of(message)).when(messageRepo).findById(1L);
        doReturn(Optional.empty()).when(messageRepo).findById(2L);

        msgService.getMessageById(1L);
        verify(messageRepo, times(1)).findById(1L);

        try{
            msgService.getMessageById(2L);
        }catch (MessageException e){
            System.out.println(e);
        }
        verify(messageRepo, times(1)).findById(2L);
    }

    /**
     * test {@link MessageService#postMessage(MessageDto)}
     */
    @Test
    public void postMessage(){

        doReturn(message).when(messageRepo).save(any(Message.class));

        msgService.postMessage(messageDto);
        verify(messageRepo, times(1)).save(message);
    }

    /**
     * test {@link MessageService#putMessage(long, MessageDto)}
     */
    @Test
    public void putMessage(){

        doReturn(Optional.of(message)).when(messageRepo).findById(1L);
        doReturn(Optional.empty()).when(messageRepo).findById(2L);
        doReturn(message).when(messageRepo).save(any(Message.class));

        msgService.putMessage(messageDto.getId(), messageDto);
        verify(messageRepo, times(1)).findById(1L);
        verify(messageRepo, times(1)).save(message);

        try{
            msgService.putMessage(2L, messageDto);
        }catch (MessageException e){
            System.out.println(e);
        }
        verify(messageRepo, times(1)).findById(2L);
    }

    /**
     * test {@link MessageService#deleteMessage(long)}
     */
    @Test
    public void deleteMessage(){

        doNothing().when(messageRepo).deleteById(1L);
        doThrow(new EmptyResultDataAccessException(1)).when(messageRepo).deleteById(2L);

        msgService.deleteMessage(1L);
        verify(messageRepo, times(1)).deleteById(1L);

        try{
            msgService.deleteMessage(2L);
        }catch (MessageException e){
            System.out.println(e);
        }
        verify(messageRepo, times(1)).deleteById(2L);
    }

    /**
     * test {@link MessageService#clearMessage()}
     */
    @Test
    public void clearMessages(){

        doNothing().when(messageRepo).deleteAll();

        msgService.clearMessage();
        verify(messageRepo, times(1)).deleteAll();
    }

}
