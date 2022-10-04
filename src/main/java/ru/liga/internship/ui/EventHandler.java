package ru.liga.internship.ui;

import java.util.Arrays;
import java.util.Scanner;

public class EventHandler {
    public static void handler(){
        Scanner scanner = new Scanner(System.in);
        Commands commandsFunctions = new Commands();
        Boolean run = true;
        while (run){
            String[] commands = scanner.nextLine().split("\\s");
            if (commands.length == 1 && commandsFunctions.getFirstStep().containsKey(commands[0])){
               run = (Boolean) commandsFunctions.getFirstStep().get(commands[0]).apply(scanner);
            } else if (commands.length == 3) {
                if (!commandsFunctions.getFirstStep().containsKey(commands[0]) || commands[0].equals("quit")){
                    System.out.println("Неверный первый аргумент: " + commands[0]);
                } else if (!commandsFunctions.getSecondStep().contains(commands[1])) {
                    System.out.println("Неверный код валюты: " + commands[1]);
                } else if (!commandsFunctions.getThirdStep().containsKey(commands[2])){
                    System.out.println("Неверный третий аргумент: " + commands[2]);
                } else{
                    System.out.println(commandsFunctions.getThirdStep().get(commands[2]).apply(commands[1]));
                }
            } else {
                System.out.println("Неверное количество аргументов");
            }
        }
    }
}
