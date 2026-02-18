package LayeredArchitectureDemo.service;

import LayeredArchitectureDemo.entity.Message;
import LayeredArchitectureDemo.exception.ErrorMessage;
import LayeredArchitectureDemo.exception.MessageException;
import LayeredArchitectureDemo.repository.MessageLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service provider of MessageController
 * Guarantees thread-safety in all methods
 * Messages are passed by reference
 */
@Service
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageLib messageLib;


    /**
     * gets all messages within the message library
     */
    public synchronized List<Message> getMessage(){
        List<Message> messageList = new ArrayList<>();
        messageLib.getLibrary().forEach((id, message) -> {
            messageList.add(message);
        });
        LOG.debug("Requested: All messages");
        return messageList;
    }

    /**
     * gets a single message
     * @param id reference id
     */
    public synchronized Message getMessageById(long id){
        Message message = messageLib.get(id);
        if (message == null) {
            LOG.debug("Message #{} does not exist", id);
            throw new MessageException(ErrorMessage.builder()
                .error("Message #" + id + " does not exist")
                .build());
        }
        LOG.debug("Requested: Message #{}", message);
        return message;
    }

    /**
     * posts a single message
     * @param message the message to be posted
     */
    public synchronized void postMessage(Message message){
        long id = message.getId();
        if (messageLib.contains(id)) {
            LOG.debug("Message #{} already exists", id);
            throw new MessageException(ErrorMessage.builder()
                .error("Message #" + id + " already exists")
                .build());
        }
        messageLib.put(message);
        LOG.debug("Created: Message #{}", message);
    }

    /**
     * puts multiple messages
     * @param messages all messages to be updated
     */
    public synchronized void putMessage(Set<Message> messages){
        List<Long> failures = new ArrayList<>();
        messages.forEach((message) -> {
            long id = message.getId();
            try {
                if (messageLib.contains(id)) {
                    LOG.debug("Updated: Message #{}", message);
                } else {
                    LOG.debug("Created: Message #{}", message);
                }
                messageLib.put(message);
            } catch (Exception e) {
                failures.add(id);
                LOG.error("Exception: {}", e.toString());
            }
        });
        if (!failures.isEmpty()) {
            throw new MessageException(ErrorMessage.builder()
                .error("Failed to update messages: ")
                .build());
        }
    }

    /**
     * deletes a single message
     * @param id reference message id
     */
    public synchronized void deleteMessage(long id){
        Message message = messageLib.get(id);
        if (message == null) {
            LOG.debug("Message #{} does not exist", id);
            throw new MessageException(ErrorMessage.builder()
                .error("Message #" + id + " does not exist")
                .build());
        }
        messageLib.remove(id);
        LOG.debug("Deleted: Message #{}", message);
    }

    /**
     * clears the message library
     */
    public synchronized void clearMessage(){
        messageLib.clear();
        LOG.debug("All messages cleared");
    }

}
