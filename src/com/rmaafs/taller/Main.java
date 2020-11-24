package com.rmaafs.taller;

public class Main {

    public static void main(String[] args) {
        BotYoutube video = new BotYoutube("https://www.youtube.com/watch?v=YcKpb7yybhY");
        System.out.println("Nombre: " + video.nombre);
        System.out.println("Vistas: " + video.vistas);
        System.out.println("Likes: " + video.likes);
        System.out.println("Dislikes: " + video.dislikes);
    }
}
