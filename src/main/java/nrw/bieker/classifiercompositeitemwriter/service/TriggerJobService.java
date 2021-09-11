package nrw.bieker.classifiercompositeitemwriter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class TriggerJobService {
    private final JobLauncher jobLauncher;
    private final Job job;

    public void runJob() throws
            JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("outputPath",
                        new JobParameter("/Users/jobiek/src/classifier-composite-itemwriter/output/"))
                .addParameter("timestamp",
                        new JobParameter(localDateTime))
                .toJobParameters();

        jobLauncher.run(job,jobParameters);

    }

}
