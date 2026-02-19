package LayeredArchitectureDemo.service;

import LayeredArchitectureDemo.entity.dto.MessageDto;

import java.util.List;
import java.util.Set;

public interface IMessageService {

    List<MessageDto> getMessage();
    MessageDto getMessageById(long id);
    void postMessage(MessageDto messageDto);
    void putMessage(long id, MessageDto messageDto);
    void deleteMessage(long id);
    void clearMessage();
}
