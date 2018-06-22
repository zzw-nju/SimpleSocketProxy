package model;

import Exception.AuthticationException;

import java.io.IOException;
import java.net.Socket;

public class DataValidate {
    public void validateAuthRequest(byte[] bytes){
        try {
            if(bytes[0]!=0x05){
                throw new AuthticationException();
            }
        } catch (AuthticationException e) {
            e.printStackTrace();
        }
    }
    public void validateAuthResponse(){

    }
    public void validateTransmitRequest(byte[] bytes){
        if(bytes[0]!=0x05||bytes[2]!=0x00){

        }
    }
    public Socket validateTransmitResponse(String host, int port){
        Socket socket=null;
        try {
            socket=new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
