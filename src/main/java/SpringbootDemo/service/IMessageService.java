package SpringbootDemo.service;

import SpringbootDemo.entity.dto.MessageDto;

import java.util.List;

public interface IMessageService {

    List<MessageDto> getMessage();
    MessageDto getMessageById(long id);
    void postMessage(MessageDto messageDto);
    void putMessage(long id, MessageDto messageDto);
    void deleteMessage(long id);
    void clearMessage();
}
