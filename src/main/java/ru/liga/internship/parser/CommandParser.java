package ru.liga.internship.parser;

import ru.liga.internship.domain.Messages;

import java.util.Stack;

public class CommandParser {
    private final Integer MIN_COMMANDS_COUNT = 6;
    private final Integer MAX_COMMANDS_COUNT = 8;

    public StateMachine parse(String commandLine) {
        Boolean isCalculable = true;
        Stack<StateAndResult> commandErrorStack = new Stack<>();
        String[] commands = commandLine.split("\\s");
        if (commands.length > MAX_COMMANDS_COUNT)
            commandErrorStack.add(new StateAndResult(false, Messages.EXCESS_OF_ARGUMENTS_MESSAGE));
        else if (commands.length < MIN_COMMANDS_COUNT)
            commandErrorStack.add(new StateAndResult(false, Messages.LACK_OF_ARGUMENTS_MESSAGE));

        Commands commandsFunctions = new Commands();

        for (int i = 0; i < commandsFunctions.getCommandLineReader().size(); i++) {
            if (commands.length == i && commands.length < MIN_COMMANDS_COUNT) {
                isCalculable = false;
                break;
            } else if (commands.length < commandsFunctions.getPrognosisLength() &&
                    commands.length >= MIN_COMMANDS_COUNT &&
                    i == commands.length) {
                commandErrorStack.add(new StateAndResult(false, Messages.LACK_OF_ARGUMENTS_MESSAGE));
                isCalculable = false;
                break;
            } else if (commands.length > commandsFunctions.getPrognosisLength() && i == commandsFunctions.getPrognosisLength()) {
                isCalculable = false;
                commandErrorStack.push(new StateAndResult(false, Messages.EXCESS_OF_ARGUMENTS_MESSAGE));
                break;

            } else if (commands.length == commandsFunctions.getPrognosisLength() && i == commandsFunctions.getPrognosisLength())
                break;
            StateAndResult stateAndResult = commandsFunctions.getCommandLineReader().get(i).apply(commands[i]);
            if (!stateAndResult.getState()) {
                isCalculable = false;
                stateAndResult.setNumber(i);
                commandErrorStack.add(stateAndResult);
            }
        }
        if (isCalculable)
            commandsFunctions.calculate();

        if (!commandErrorStack.empty()) {
            StateMachine stateMachine = new StateMachine("error");
            stateMachine.setErrorStack(commandErrorStack);
            commandsFunctions.getStates().add(stateMachine);
        }
        return commandsFunctions.getStates().pop();
    }
}
