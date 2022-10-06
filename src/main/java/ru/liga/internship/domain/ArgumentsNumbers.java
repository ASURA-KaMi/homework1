package ru.liga.internship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ArgumentsNumbers {
    FIRST("первый", "first"),
    SECOND("второй", "second"),
    THIRD("третий", "third"),
    FOURTH("четвертый", "fourth"),
    FIFTH("пятый", "fifth"),
    SIXTH("шестой", "sixth"),
    SEVENTH("седьмой", "seventh"),
    EIGHTH("восьмой", "eighth"),
    NULL("","");
    private final String russian;
    private final String english;

    public ArgumentsNumbers getByNumber(Integer number){
        return Arrays.stream(ArgumentsNumbers.values()).skip(number).findFirst().orElse(ArgumentsNumbers.NULL);
    }
}
