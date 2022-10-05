package ru.liga.internship.ui;


import java.util.Scanner;

public class EventHandler {
    public static void handler(){
        Scanner scanner = new Scanner(System.in);
        Boolean run = true;
        while (run) {
            String[] commands = scanner.nextLine().split("\\s");
            Commands commandsFunctions = new Commands();
            Integer prognosisLength = 0;
            for (int i = 0; i < commands.length; i++){
                StateAndResult stateAndResult = commandsFunctions.getCommandLineReader().get(i).apply(commands[i]);
                if (!stateAndResult.getState()) {
                    System.out.println(stateAndResult.getResult());
                    break;
                }
                prognosisLength = stateAndResult.getPrognosisLength();
            }
            if (prognosisLength > commands.length)
                System.out.println("Недостаточное количество аргументов");
        }

    }
}
