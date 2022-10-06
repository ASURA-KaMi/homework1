package ru.liga.internship.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CurrencyCode {
    EUR,
    USD,
    TRY,
    BGN,
    AMD;
    public String getCodes(){
        return "[" + Arrays.stream(CurrencyCode.values()).map(CurrencyCode::name).collect(Collectors.joining(",")) + "]";
    }
}
