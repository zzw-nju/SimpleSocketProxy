package transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferDataRunnable implements Runnable {
    private InputStream inputStream;
    private OutputStream outputStream;
    public TransferDataRunnable(InputStream inputStream,OutputStream outputStream) {
        this.inputStream =inputStream;
        this.outputStream =outputStream;
    }
    private byte[] buffer=new byte[4028000];
    @Override
    public void run(){
        int len;
        try{
            while ((len = inputStream.read(buffer)) != -1) {
                if (len > 0) {
                    System.out.println(new String(buffer, 0, len));
                    outputStream.write(buffer, 0, len);
                    outputStream.flush();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
