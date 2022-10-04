package ru.liga.internship.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvUtils {
    private final String FILE_FORMAT = ".csv";
    private final String CSV_SPLITTER = ";";
    private final String STRING_DELIMITER = "\"";
    private String currencyFileName;
    private List<String> fields;
    private InputStream currencyRegister;
    private InputStreamReader currencyRegisterReader;

    public CsvUtils(String currencyName){
        this.currencyFileName = currencyName + FILE_FORMAT;
        setupReaders();
        this.fields = getFieldsFromCsv();
    }
    private void setupReaders(){
        this.currencyRegister = FileResourcesUtils.getFileFromResourceAsStream(currencyFileName);
        this.currencyRegisterReader = new InputStreamReader(currencyRegister, StandardCharsets.UTF_8);
    }
    public List<Map<String,String>> getValuesList(Integer numberOfValues) {
        setupReaders();
        BufferedReader reader = new BufferedReader(currencyRegisterReader);
        return reader.lines().skip(1).limit(numberOfValues).map(this::getValueFromString).toList();
    }

    private Map<String,String> getValueFromString(String rawData){
        Map<String,String> values = new HashMap<>();
        List<String> rawValues = Arrays.stream(rawData.split(CSV_SPLITTER)).toList();
        for (int i = 0; i < fields.size(); i++){
            values.put(fields.get(i), removeStringDelimiters(rawValues.get(i)));
        }
        return values;
    }
    private List<String> getFieldsFromCsv(){
        return Arrays.stream(getFirstString().split(CSV_SPLITTER)).map(this::removeStringDelimiters).toList();
    }

    private String removeStringDelimiters(String string){
        return string.replaceAll(STRING_DELIMITER, "");
    }

    private String getFirstString() {
        BufferedReader reader = new BufferedReader(currencyRegisterReader);
        String returnValue;
        try {
            returnValue = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return returnValue;
    }
}
