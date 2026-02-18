package LayeredArchitectureDemo.repository;

import LayeredArchitectureDemo.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * unit testing {@link MessageLib}
 */
public class MessageLibTest {

    private MessageLib messageLib;

    private ObjectMapper objMapper = new ObjectMapper();

    @BeforeEach
    public void reset(){
        messageLib = new MessageLib();
    }

    @Test
    public void crudTest() throws JsonProcessingException {

        Message message1 = new Message();
        Message message2 = new Message();
        message1.setId(1L);
        message2.setId(2L);

        messageLib.put(message1);
        messageLib.put(message2);
        assertThat(messageLib.get(1L).getId()).isEqualTo(1L);
        assertThat(messageLib.get(2L).getId()).isEqualTo(2L);

        messageLib.remove(2L);
        messageLib.replace(message2);
        assertThat(messageLib.contains(1L)).isTrue();
        assertThat(messageLib.contains(2L)).isFalse();
        assertThat(messageLib.size()).isEqualTo(1);
        messageLib.toString();

        messageLib.clear();
        assertThat(messageLib.size()).isEqualTo(0);

        HashMap<Long, Message> library = new HashMap<>();
        messageLib.setLibrary(library);
        String expected = objMapper.writeValueAsString(library);
        String actual = objMapper.writeValueAsString(messageLib.getLibrary());
        assertThat(actual).isEqualTo(expected);
    }

}
