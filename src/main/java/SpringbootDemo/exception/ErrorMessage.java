package SpringbootDemo.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Error message among APIs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage {
    private HttpStatus status;
    private String error;
    private String uri;
    private Instant timestamp;

    public ErrorMessage() {}

    public ErrorMessage(HttpStatus status, String error, String uri, Instant timestamp) {
        this.status = status;
        this.error = error;
        this.uri = uri;
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private HttpStatus status;
        private String error;
        private String uri;
        private Instant timestamp;

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorMessage build() {
            return new ErrorMessage(status, error, uri, timestamp);
        }
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", uri='" + uri + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}