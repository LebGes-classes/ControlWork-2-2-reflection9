package org.example;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Program> allProgram = new ArrayList<>();
        Deserialization deserialization = new Deserialization();
        ArrayList<String> fileData;

        try {
            fileData = deserialization.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String curChannel = null;

        BroadcastsTime curTime = null;
        for (String file : fileData) {
            if (file.contains("#")) {
                curChannel = file;
            }
            else if (file.contains(":") && file.length() == 5) {
                curTime = new BroadcastsTime(file);
            } else {
                allProgram.add(new Program(curChannel, curTime, file));
            }
        }
        sortTime(allProgram);

        Serialization writer = new Serialization(allProgram, "отсортированные_программы.xlsx");
        Serialization.saveToExcel(allProgram, "отсортированные_программы.xlsx");
        printCurrentPrograms(allProgram);
    }
    public static void printCurrentPrograms(ArrayList<Program> allProgram) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String currentTimeString = currentTime.format(timeFormatter);
        BroadcastsTime currentBroadcastsTime = new BroadcastsTime(currentTimeString);

        ArrayList<Program> currentPrograms = curProgram(allProgram, currentTimeString);
        System.out.println("Программы, которые идут в 16:00:");
        for (Program program : currentPrograms) {
            if(currentTime.equals("16:0")){
                System.out.println(program);
            }
        }
    }

    public static ArrayList<Program> sortTime(ArrayList<Program> allProgram){
        allProgram.sort((program1, program2) -> {
            int timeComparison = program1.getTime().compareTo(program2.getTime());
            return timeComparison;
        });
        return allProgram;
    }
    public static ArrayList<Program> curProgram(ArrayList<Program> allProgram, String curTime){
        BroadcastsTime curBroadcastsTime = new BroadcastsTime(curTime);
        ArrayList<Program> curProg = new ArrayList<>();
        for (Program program : allProgram) {
            if (program.getTime().compareTo(curBroadcastsTime) == 0) {
                curProg.add(program);
            }
        }
        return curProg;
    }
    public static ArrayList<Program> programBetween(ArrayList<Program> allProgram, String time1, String time2, String channel) {
        BroadcastsTime broadcastsTime1 = new BroadcastsTime(time1);
        BroadcastsTime broadcastsTime2 = new BroadcastsTime(time2);
        ArrayList<Program> programsBetween = new ArrayList<>();
        for (Program program : allProgram) {
            if (program.getChannel().equals(channel) && program.getTime().compareTo(broadcastsTime1) >= 0 && program.getTime().compareTo(broadcastsTime2) <= 0) {
                programsBetween.add(program);
            }
        }
        return programsBetween;
    }
}