package godofjava2nd.Chapter29.manager;

import godofjava2nd.Chapter29.dto.RequestDTO;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RequestManager {

    private final Socket socket;

    public RequestManager(Socket socket) {
        this.socket = socket;
    }

    private static final int BUFFER_SIZE = 2048;

    public RequestDTO readRequest() {
        try(InputStream request = new BufferedInputStream(socket.getInputStream())) {
            byte[] receivedBytes = new byte[BUFFER_SIZE];
            request.read(receivedBytes);
            String requestData = new String(receivedBytes).trim();
            System.out.println("RequestData = \n" + requestData);
            System.out.println("----------");

            String[] headers = getHeaders(requestData);
            return requestBuilder(headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RequestDTO();
    }

    private RequestDTO requestBuilder(String[] headers) {
        return RequestDTO.Builder()
            .requestMethod(headers[0])
            .uri(headers[1])
            .httpVersion(headers[2])
            .build();
    }

    private String[] getHeaders(String requestData) {
        return requestData.split("\n")[0].split(" ");
    }

}
