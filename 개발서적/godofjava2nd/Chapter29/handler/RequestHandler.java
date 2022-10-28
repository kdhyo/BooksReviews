package godofjava2nd.Chapter29.handler;

import godofjava2nd.Chapter29.dto.RequestDTO;
import godofjava2nd.Chapter29.manager.RequestManager;
import godofjava2nd.Chapter29.manager.ResponseManager;
import java.net.Socket;

public class RequestHandler extends Thread {


    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        RequestManager requestManager = new RequestManager(socket);
        RequestDTO requestDTO = requestManager.readRequest();
        ResponseManager responseManager = new ResponseManager(socket, requestDTO);
        responseManager.writeResponse();
    }

}
