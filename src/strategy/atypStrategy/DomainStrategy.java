package strategy.atypStrategy;

import strategy.ATYPStrategy;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DomainStrategy implements ATYPStrategy {
    private int length;
    public DomainStrategy(int length) {
        this.length=length;
    }
    @Override
    public String getHost(byte[] bytes) {
        StringBuffer sb=new StringBuffer();
        char c;
        for (int i =5; i < length+5; i++) {
            c=(char)(0xFF & bytes[i]);
            sb.append(c);
        }
        System.out.println("host:--------"+sb.toString());
        return sb.toString();
    }
    @Override
    public int getPort(byte[] bytes) {
        int port = 0;
        for (int i = length+5; i < length+7; i++) {
            port <<= 8;
            port += (0xFF&bytes[i]);
        }
        System.out.println("port:--------"+port);
        return port;
    }
    @Override
    public byte[] getResponse(byte[] bytes,byte[] connect){
        byte[] result=new byte[4];
        InetAddress address = null;
        try {
            address=InetAddress.getByName(getHost(bytes));
            result=address.getAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        for (int i = 4; i <8; i++) {
            connect[i] = result[i-4];
        }
        connect[8]=bytes[length+5];
        connect[9]=bytes[length+6];
        System.out.println("response------------"+connect.toString());
        return connect;
    }
}
