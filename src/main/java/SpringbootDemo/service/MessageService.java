package SpringbootDemo.service;

import SpringbootDemo.entity.Message;
import SpringbootDemo.entity.dto.MessageDto;
import SpringbootDemo.exception.ErrorMessage;
import SpringbootDemo.exception.MessageException;
import SpringbootDemo.repository.MessageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service provider of MessageController
 * Guarantees thread-safety in all methods
 * Messages are passed by reference
 */
@Service
public class MessageService implements IMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepo messageRepo;


    /**
     * gets all messages within the message library
     */
    @Override
    public synchronized List<MessageDto> getMessage(){
        List<MessageDto> messageList = new ArrayList<>();
        for (Message message: messageRepo.findAll()) {
            MessageDto messageDto = new MessageDto();
            BeanUtils.copyProperties(message, messageDto);
            messageList.add(messageDto);
        }
        LOG.debug("Requested: All messages");
        return messageList;
    }

    /**
     * gets a single message
     * @param id reference id
     */
    @Override
    public synchronized MessageDto getMessageById(long id){
        Message message = messageRepo.findById(id).orElseThrow(() -> {
            LOG.debug("Message #{} does not exist", id);
            return new MessageException(
                    ErrorMessage.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .error("Message #" + id + " does not exist")
                            .build());
        });
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        LOG.debug("Requested: Message #{}", message);
        return messageDto;
    }

    /**
     * posts a single message
     * @param messageDto the message to be posted
     */
    @Override
    public synchronized void postMessage(MessageDto messageDto){
        Message message = new Message();
        BeanUtils.copyProperties(messageDto, message);
        messageRepo.save(message);
        LOG.debug("Created: Message #{}", message);
    }

    /**
     * puts multiple messages
     * @param messageDto the message to be updated
     */
    @Override
    public synchronized void putMessage(long id, MessageDto messageDto){
        Message message = messageRepo.findById(id).orElseThrow(() -> {
            LOG.debug("Message #{} does not exist", id);
            return new MessageException(
                    ErrorMessage.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .error("Message #" + id + " does not exist")
                            .build());
        });
        BeanUtils.copyProperties(messageDto, message);
        message.setId(id);
        messageRepo.save(message);
    }

    /**
     * deletes a single message
     * @param id reference message id
     */
    @Override
    public synchronized void deleteMessage(long id){
        try {
            messageRepo.deleteById(id);
        }catch (Exception e){
            throw new MessageException(
                    ErrorMessage.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .error("Message #" + id + " does not exist")
                            .build());
        }
        LOG.debug("Deleted: Message #{}", id);
    }

    /**
     * clears the message library
     */
    @Override
    public synchronized void clearMessage(){
        messageRepo.deleteAll();
        LOG.debug("All messages cleared");
    }

}
