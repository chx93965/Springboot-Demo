package SpringbootDemo.repository;

import SpringbootDemo.entity.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * stores {@link Message} in the form of (id, message)
 * guarantees thread safety in accessing
 * through synchronized functions on the service layer
 */
@Repository
@Scope("singleton")
public class MessageLib {

    private HashMap<Long, Message> library = new HashMap<>();

    public void put(Message message){
        library.put(message.getId(), message);
    }

    public Message get(long id){
        return library.get(id);
    }

    public void remove(long messageId){
        library.remove(messageId);
    }

    public void replace(Message message){
        library.replace(message.getId(), message);
    }

    public boolean contains(long id){
        return library.containsKey(id);
    }

    public void clear(){
        library.clear();
    }

    public int size(){
        return library.size();
    }

    public HashMap<Long, Message> getLibrary() {
        return library;
    }

    public void setLibrary(HashMap<Long, Message> library) {
        this.library = library;
    }

    /**
     * @return all {@link Message}'s each residing at a separate line
     */
    @Override
    public String toString() {
        String output = "\nmessageLib{\n";
        for(long messageId : library.keySet()){
            output += messageId + ": " + library.get(messageId) + "\n";
        }
        output += "}";
        return output;
    }
}
