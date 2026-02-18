package LayeredArchitectureDemo.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * The message data processed within the application
 */
@Table(name="tb_msg")
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected long id;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_data", joinColumns = @JoinColumn(name = "msg_id"))
    @Column(nullable = false)
    protected List<String> data;

    @Column
    protected String info;

    public Message() {}

    public Message(long id, List<String> data, String info) {
        this.id = id;
        this.data = data;
        this.info = info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id && Objects.equals(data, message1.data) && Objects.equals(info, message1.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, info);
    }

    @Override
    public String toString() {
        return "Message{" +
                "\n\tid=" + id +
                "\n\tdata=" + data +
                "\n\tmessage='" + info +
                "\n}";
    }
}
