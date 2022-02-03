package godofjava2nd.Chapter29.manager;

import godofjava2nd.Chapter29.dto.RequestDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseManager {

    private final Socket socket;
    private final RequestDTO requestDTO;

    public ResponseManager(Socket socket, RequestDTO requestDTO) {
        this.socket = socket;
        this.requestDTO = requestDTO;
    }

    public void writeResponse() {
        try (PrintStream response = new PrintStream(socket.getOutputStream())) {
            response.println("HTTP/1.1 200 OK");
            response.println("Content-type: text/html");
            response.println();
            setBody(response);

            response.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBody(PrintStream response) {
        switch (requestDTO.getUri()) {
            case "/today":
                LocalDateTime now = LocalDateTime.now();
                response.print(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                break;
            default:
                response.print("It is working");
                break;
        }
    }

}
