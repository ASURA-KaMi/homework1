package ru.liga.internship.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateAndResult {
    private Boolean state;
    private Messages messages;
    private Integer number;
    private String errorPart;

    public StateAndResult(Boolean state, Messages messages){
        this.messages = messages;
        this.state = state;
        this.errorPart = "";
    }
}
