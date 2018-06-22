package model;

import strategy.atypStrategy.DomainStrategy;
import strategy.atypStrategy.IpvfStrategy;
import strategy.ATYPStrategy;
import transport.TransferDataRunnable;
import utils.ConvertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketHandlerRunnable implements Runnable{
    private InputStream inputStreamFromClient;
    private OutputStream outputStreamToClient;
    private Socket socketClient;
    private InputStream inputStreamFromServer;
    private OutputStream outputStreamToServer;
    private Socket socketServer;
    private ConvertUtils convertUtils=new ConvertUtils();
    private DataValidate validate=new DataValidate();
    private byte[] bytes = new byte[270];
    private static final byte[] AUTHRESPONSE = { 0x05, 0x00 };
    private static final byte[] CONNECT = { 0x05, 0x00,0x00,0x01,0,0,0,0 ,0,0};
    public SocketHandlerRunnable(Socket socket){
        this.socketClient =socket;
    }
    //认证权限
    public void confirmAuth(){
        System.out.println("\n\n " + socketClient.getInetAddress() + ":" + socketClient.getPort());
        try {
            inputStreamFromClient = socketClient.getInputStream();
            outputStreamToClient = socketClient.getOutputStream();
            int len = inputStreamFromClient.read(bytes);
            validate.validateAuthRequest(bytes);
            outputStreamToClient.write(AUTHRESPONSE);
            outputStreamToClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //转发请求
    public void transmit() throws IOException{
        int len = inputStreamFromClient.read(bytes);
        validate.validateTransmitRequest(bytes);
        ATYPStrategy ATYPStrategy;
        if(bytes[3]==0x03){
            ATYPStrategy =new DomainStrategy(bytes[4]);
        }else{
            ATYPStrategy =new IpvfStrategy();
        }
        socketServer = validate.validateTransmitResponse(ATYPStrategy.getHost(bytes), ATYPStrategy.getPort(bytes));
        inputStreamFromServer = socketServer.getInputStream();
        outputStreamToServer = socketServer.getOutputStream();
        outputStreamToClient.write(ATYPStrategy.getResponse(bytes,CONNECT));
        outputStreamToClient.flush();
    }
    @Override
    public void run() {
        try {
            confirmAuth();
            transmit();
            Thread sourceThread=new Thread(new TransferDataRunnable(inputStreamFromClient, outputStreamToServer));
            sourceThread.start();
            Thread responseThread=new Thread(new TransferDataRunnable(inputStreamFromServer, outputStreamToClient));
            responseThread.start();
            sourceThread.join();
            responseThread.join();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if (socketClient !=null) {
                    socketClient.close();
                    socketClient =null;
                }
                if (socketServer != null) {
                    socketServer.close();
                    socketServer =null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
