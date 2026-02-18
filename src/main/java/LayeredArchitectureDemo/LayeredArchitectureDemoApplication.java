package LayeredArchitectureDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LayeredArchitectureDemoApplication {

    private static final Logger LOG = LoggerFactory.getLogger(LayeredArchitectureDemoApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(LayeredArchitectureDemoApplication.class, args);
    }

}

