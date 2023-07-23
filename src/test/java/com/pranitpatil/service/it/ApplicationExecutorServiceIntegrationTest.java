package com.pranitpatil.service.it;

import com.pranitpatil.service.ApplicationExecutorService;
import com.pranitpatil.service.ApplicationExecutorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationExecutorServiceIntegrationTest {

    private ApplicationExecutorService applicationExecutorService;
    
    @ParameterizedTest
    @CsvSource({"orders1.csv,result1.txt", 
            "orders2.csv,result2.txt",
            "orders3.csv,result3.txt",
            "orders4.csv,result4.txt",
            "orders5.csv,result5.txt",
    })
    public void testScenario(String inputFileName, String outputfileName) throws URISyntaxException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        Path outputFilePath = Paths.get(getClass().getClassLoader()
                .getResource(outputfileName).toURI());
        String output = Files.readString(outputFilePath);

        Path inputFilePath = Paths.get(getClass().getClassLoader()
                .getResource(inputFileName).toURI());
        InputStream inputStream = new ByteArrayInputStream((Files.readString(inputFilePath).getBytes()));
        new ApplicationExecutorServiceImpl(inputStream).startApplication();

        assertEquals(output, baos.toString().trim());
    }
}
