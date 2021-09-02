package nrw.bieker.classifiercompositeitemwriter.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nrw.bieker.classifiercompositeitemwriter.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public BatchConfigurer batchConfigurer(){
        return new DefaultBatchConfigurer(dataSource());
    }


    private DataSource dataSource() {
        return DataSourceBuilder
                .create().url("jdbc:h2:tcp://localhost/~/src/classifier-composite-itemwriter/test")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("test")
                .type(HikariDataSource.class).build();
    }


    @Bean
    public FlatFileItemWriter<Person> flatFileItemWriter() throws Exception{
        return new FlatFileItemWriterBuilder<Person>()
                .name("test-classifier-builder")
                .resource(new FileSystemResource("person.csv"))
                .delimited()
                .delimiter(";")
                .names(FIELDS)
                .headerCallback(new StringHeaderWriter(toStringFieldNames(FIELDS)))
                .encoding("windows-1250")
                .shouldDeleteIfExists(true)
                .build();
    }


    private JdbcCursorItemReader<Person> reader() {
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("classifier-jdbc-cursor-item-reader")
                .dataSource(dataSource())
                .sql("Select id, name from person")
                .beanRowMapper(Person.class)
                .build();
    }

    public String toStringFieldNames(String[] fields) {
        try {
            StringBuilder sb = new StringBuilder();

            for(String field : fields) {
                sb.append(field.concat(";"));
            }
            return sb.toString();

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Bean
    public Step step() throws Exception {
        Step step = stepBuilderFactory.get("classifier-composite-demo-step")
                .<Person,Person>chunk(1)
                .reader(reader())
                .writer(flatFileItemWriter())
                .build();
        return step;
    }

    @Bean
    public Job job() throws Exception {
        Job job  = jobBuilderFactory.get("classifier-composite-demo-job")
                .start(step())
                .build();
        return job;
    }

    private final String[] FIELDS = {"id", "name"};
}
