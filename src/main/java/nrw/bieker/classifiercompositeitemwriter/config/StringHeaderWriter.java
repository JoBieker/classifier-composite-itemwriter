package nrw.bieker.classifiercompositeitemwriter.config;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class StringHeaderWriter implements FlatFileHeaderCallback {

    private final String header;

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }

}
