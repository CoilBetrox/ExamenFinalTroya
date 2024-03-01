package com.distribuida.authors;

import io.helidon.microprofile.server.Server;

public final class Main {
    private Main(){}

    public static void main(String[] args) {
        Server server = startServer();
        System.out.println("http://localhost:"+server.port());
    }

    static Server startServer(){
        return Server.create().start();
    }
}
