package godofjava2nd.Chapter29.server;

import godofjava2nd.Chapter29.handler.RequestHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServer {

    public static void main(String[] args) {
        SimpleWebServer server = new SimpleWebServer();
        int port = 9999;
        server.run(port);
    }

    private void run(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            serverStart(server);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void serverStart(ServerSocket server) throws IOException {
        while (true) {
            Socket socket = server.accept();
            RequestHandler handler = new RequestHandler(socket);
            handler.start();
        }
    }

}
