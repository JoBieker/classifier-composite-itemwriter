package nrw.bieker.classifiercompositeitemwriter.config;

import nrw.bieker.classifiercompositeitemwriter.model.Person;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.classify.Classifier;

public class PersonClassifier implements Classifier<Person, ItemWriter<? super Person>> {

    /**
     * Minella, Michael T.. The Definitive Guide to Spring Batch (Kindle-Positionen9130-9131). Apress. Kindle-Version.
     */
    private ItemWriter<Person> evenItemWriter;
    private ItemWriter<Person> oddItemWriter;

    public PersonClassifier(FlatFileItemWriter<Person> evenItemWriter, FlatFileItemWriter<Person> oddItemWriter) {
        this.evenItemWriter = evenItemWriter;
        this.oddItemWriter = oddItemWriter;
    }

    @Override
    public ItemWriter<Person> classify(Person person) {
        if(person.getId() % 2 == 0) return evenItemWriter;
        return oddItemWriter;
    }
}
