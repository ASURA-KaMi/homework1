package ru.liga.internship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.internship.utils.DateUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MonetaryUnit {

    private final Byte multiplier;

    private final LocalDate date;

    private final Float rate;

    private final String cdx;

    public Float getRatePerUnit(){
        return this.rate / this.multiplier;
    }

    public MonetaryUnit(Map<String,String> values){
        this.multiplier = Byte.valueOf(values.get("nominal"));
        this.date = DateUtils.localDateFromString(values.get("data"));
        this.rate = Float.valueOf(values.get("curs"));
        this.cdx = values.get("cdx");
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("0.00");
        return DateUtils.stringFromLocalDate(date) + " - " + formatter.format(getRatePerUnit());
    }
}
