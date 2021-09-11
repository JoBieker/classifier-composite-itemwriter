package nrw.bieker.classifiercompositeitemwriter;

import nrw.bieker.classifiercompositeitemwriter.service.TriggerJobService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableBatchProcessing
public class ClassifierCompositeItemwriterApplication {

    public static void main(String[] args) throws
            JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(ClassifierCompositeItemwriterApplication.class, args);

        TriggerJobService triggerJobService = applicationContext.getBean(TriggerJobService.class);
        triggerJobService.runJob();
    }

}
