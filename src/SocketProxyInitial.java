import model.SocketHandlerRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketProxyInitial {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(1080);
            ExecutorService executorService=Executors.newFixedThreadPool(100);
            while(true){
                Socket socket=serverSocket.accept();
                Thread thread=new Thread(new SocketHandlerRunnable(socket));
                executorService.submit(thread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
