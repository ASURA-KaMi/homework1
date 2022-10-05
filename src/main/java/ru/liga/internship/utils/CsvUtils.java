package ru.liga.internship.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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


    public List<Map<String,String>> getValuesList(Integer dayOffset,Integer numberOfValues) {
        setupReaders();
        BufferedReader reader = new BufferedReader(currencyRegisterReader);
        return reader.lines().skip(1 + dayOffset - numberOfValues).limit(numberOfValues).map(this::getValueFromString).toList();
    }

    public Map<String,String> findValuesByDate(LocalDate date) {
            setupReaders();
            BufferedReader reader = new BufferedReader(currencyRegisterReader);
            return reader.lines().skip(1)
                    .map(this::getValueFromString)
                    .filter(values -> {
                        LocalDate valueDate = DateUtils.localDateFromString(values.get("data"));
                        return valueDate.isEqual(date) || valueDate.isBefore(date);
                    })
                    .findFirst()
                    .orElse(new HashMap<>());
    }

    public List<Integer> findMinMaxDate(){
        setupReaders();
        List<Integer> minMax = new ArrayList<>();
        BufferedReader reader = new BufferedReader(currencyRegisterReader);
        minMax.add(reader.lines()
                .skip(1)
                .map(this::getValueFromString)
                .map(values -> DateUtils.localDateFromString(values.get("data")))
                .min(LocalDate::compareTo)
                .orElse(LocalDate.MIN).getYear() + 1);
        minMax.add(getValuesList(1,1).stream()
                .map(values -> DateUtils.localDateFromString(values.get("data")))
                .findFirst()
                .orElse(LocalDate.MAX).getYear());
        return minMax;
    }
    public Integer findCurrencyIndexByDay(LocalDate date){
        setupReaders();
        BufferedReader reader = new BufferedReader(currencyRegisterReader);
        try {
            String rawString = reader.readLine();
            Map<String,String> values = new HashMap<>();
            LocalDate valueDate;
            Integer i = 0;
            while (!rawString.isBlank()){
                i++;
                rawString = reader.readLine();
                values = getValueFromString(rawString);
                valueDate = DateUtils.localDateFromString(values.get("data"));
                if (valueDate.isEqual(date) || valueDate.isBefore(date))
                    return i;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
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
