package LayeredArchitectureDemo.controller;

import LayeredArchitectureDemo.entity.Message;
import LayeredArchitectureDemo.service.MessageService;
import LayeredArchitectureDemo.repository.MessageLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Validated
@RestController
@RequestMapping("/msg")
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService msgService;

    /**
     * gets the general info of all messages within the message library
     * @return 200 Ok as the success message
     * @return 500 Internal Server Error in case of any exceptions
     */
    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Message>> getMessage() {

        LOG.trace("GET /msg called");
        List<Message> messageList = msgService.getMessage();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    /**
     * gets the general info of a message
     * @return 200 Ok as the success message
     * @return 400 Bad Request if the given msgId does not exist
     * @return 500 Internal Server Error in case of any exceptions
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(
            @NotNull(message = "Message id cannot be null")
            @PathVariable long id){

        LOG.trace("GET /msg/{id} called");
        Message message = msgService.getMessageById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * posts a single message
     * @return 201 Created when successfully created a new {@link Message}
     * @return 400 Bad Request if a Message with the same id already exists
     * @return 500 Internal Server Error in case of any exceptions
     */
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> postMessage(
            @NotNull(message = "Message cannot be null")
            @Valid @RequestBody Message message){

        LOG.trace("POST /msg called");
        msgService.postMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * puts multiple messages
     * @return 200 Ok when successfully updated existing {@link Message}'s
     * @return 500 Internal Server Error alongside a failure list
     * containing Messages that are not successfully updated
     */
    @PutMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> putMessage(
            @NotNull(message = "Messages cannot be null")
            @Valid @RequestBody Set<Message> messages){

        LOG.trace("PUT /msg called");
        msgService.putMessage(messages);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * deletes a single message
     * @return 200 Ok as the success message
     * @return 400 Bad Request if the given id does not exist
     * @return 500 Internal Server Error in case of any exceptions
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteMessage(
            @NotNull(message = "Message id cannot be null")
            @PathVariable long id){

        LOG.trace("DELETE /msg/{id} called");
        msgService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * clears {@link MessageLib}
     * @return 200 Ok as the success message
     * @return 500 Internal Server Error in case of any exceptions
     */
    @DeleteMapping(value = "")
    public ResponseEntity<Void> clearMessage(){

        LOG.trace("DELETE /msg called");
        msgService.clearMessage();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
