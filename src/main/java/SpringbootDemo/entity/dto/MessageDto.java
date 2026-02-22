package SpringbootDemo.entity.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MessageDto {
    protected long id;
    @NotNull(message = "Data cannot be null")
    protected List<String> data;
    @Length(max = 100)
    protected String info;

    public MessageDto() {}

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
    public String toString() {
        return "MessageDto{" +
                "data=" + data +
                ", info='" + info + '\'' +
                '}';
    }
}
