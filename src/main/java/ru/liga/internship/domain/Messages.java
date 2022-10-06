package ru.liga.internship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {
    LACK_OF_ARGUMENTS_MESSAGE("Недостаточное количество аргументов.","Insufficient number of arguments."),
    EXCESS_OF_ARGUMENTS_MESSAGE("Число аргументов превышено.","The number of arguments exceeded."),
    INVALID_ARGUMENT("Неправильный аргумент: ", "Incorrect argument: "),
    INVALID_CURRENCY_CODE("Неправильный код валюты: ", "Incorrect currency code: "),
    INVALID_FORECASTING("Невозможно составить прогноз на заданный день.","It is impossible to make a forecast for a given day."),
    HELP_MESSAGE("rate " + CurrencyCode.AMD.getCodes() + "\n" +
            "-date [tomorrow,22.01.2000] или -period " + ForecastRange.week.getRanges() + "\n" +
            "-alg " + ForecastType.expl.getTypes() + "\n" +
            "(Если используется флаг -period " + ForecastRange.week.getRanges() + "\n" +
            "Добавьте фалг вывода -output [list,graph])",
            "rate " + CurrencyCode.AMD.getCodes() + "\n" +
                    "-date [tomorrow,22.01.2000] or -period " + ForecastRange.week.getRanges() + "\n" +
                    "-alg " + ForecastType.expl.getTypes() + "\n" +
                    "(In case when used flag -period " + ForecastRange.week.getRanges() + "\n" +
                    "Add the output format flag -output [list,graph])");

    private final String russian;
    private final String english;

    public String getRussianInvalidByNumber(Integer number) {
        StringBuilder stringBuilder = new StringBuilder(Messages.INVALID_ARGUMENT.russian);
        stringBuilder.insert(stringBuilder.indexOf(" "), " " + ArgumentsNumbers.NULL.getByNumber(number).getRussian());
        return stringBuilder.toString();
    }
    public String getEnglishInvalidByNumber(Integer number) {
        StringBuilder stringBuilder = new StringBuilder(Messages.INVALID_ARGUMENT.english);
        stringBuilder.insert(stringBuilder.indexOf(" "), " " + ArgumentsNumbers.NULL.getByNumber(number).getEnglish());
        return stringBuilder.toString();
    }
}
