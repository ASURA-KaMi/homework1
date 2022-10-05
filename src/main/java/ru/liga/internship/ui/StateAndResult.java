package ru.liga.internship.ui;

import lombok.Getter;
import lombok.Setter;
import ru.liga.internship.utils.CsvUtils;

import java.util.List;

@Getter
@Setter
public class StateAndResult {
    private Boolean state;
    private String result;
    private Integer prognosisLength;
}
