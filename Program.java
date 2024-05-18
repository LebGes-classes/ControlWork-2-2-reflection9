package org.example;

public class Program{
    private String channel;
    private BroadcastsTime time;
    private String name;
    Program(String channel, BroadcastsTime time, String name){
        this.channel = channel;
        this.time = time;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Программа{" +
                "Канал ='" + channel + '\'' +
                ", Время =" + time +
                ", Название ='" + name + '\'' +
                '}';
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public BroadcastsTime getTime() {
        return time;
    }

    public void setTime(BroadcastsTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}