package ru.liga.internship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CurrencyCode {
    EUR("Евро", new Color(104,254,0)),
    USD("Доллар США", new Color(255,255,0) ),
    TRY("Турецкая лира", new Color(117,186,218)),
    BGN ("Болгарский лев", new Color(236,186,218)),
    AMD ("Армянский драм", new Color(236,120,135));

    private String name;
    private Color chartColor;
    private static Map<String, CurrencyCode> statuses;

    static {
        statuses = new HashMap<>();
        Stream.of(CurrencyCode.values()).forEach(s -> statuses.put(s.name, s));
    }

    public CurrencyCode getCodeByName(String name){
        return statuses.get(name);
    }
    public String getCodes(){
        return "[" + Arrays.stream(CurrencyCode.values()).map(CurrencyCode::name).collect(Collectors.joining(",")) + "]";
    }
}
