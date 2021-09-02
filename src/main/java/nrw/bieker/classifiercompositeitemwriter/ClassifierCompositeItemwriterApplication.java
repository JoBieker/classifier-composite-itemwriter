package nrw.bieker.classifiercompositeitemwriter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ClassifierCompositeItemwriterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifierCompositeItemwriterApplication.class, args);
    }

}
