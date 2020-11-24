package com.rmaafs.taller;

public class Main {

    public static void main(String[] args) {
        BotInstagram cuenta = new BotInstagram("https://www.instagram.com/relmaps/?hl=mx");
        System.out.println("Nombre: " + cuenta.nombre);
        System.out.println("Cuenta: " + cuenta.cuenta);
        System.out.println("Followers: " + cuenta.followers);
        System.out.println("Following: " + cuenta.following);
    }
}
