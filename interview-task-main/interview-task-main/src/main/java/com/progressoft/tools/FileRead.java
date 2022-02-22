package com.progressoft.tools;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
public class FileRead {

    public List<String[]> parse(Path path){

        List<String[]> values = null;
        try (FileReader file = new FileReader(path.toFile())) {
            try (CSVReader reader = new CSVReader(file)) {
                values = reader.readAll();
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return values;
    }
}
