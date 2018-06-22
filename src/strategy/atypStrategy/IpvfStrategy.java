package strategy.atypStrategy;

import strategy.ATYPStrategy;

public class IpvfStrategy implements ATYPStrategy {
    private StringBuffer sb;
    private static final byte[] CONNECT = { 0x05, 0x00,0x00,0x01,0,0,0,0 ,0,0};
    public IpvfStrategy() {
        sb=new StringBuffer();
    }
    @Override
    public String getHost(byte[] bytes) {
        for (int i = 4; i < 8; i++) {
            sb.append(Integer.toString(0xFF & bytes[i]));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println("host:--------"+sb.toString());
        return sb.toString();
    }

    @Override
    public int getPort(byte[] bytes) {
        int port=0;
        for (int i = 8; i < 10; i++) {
            port <<= 8;
            port += (0xFF&bytes[i]);
        }
        System.out.println("port:--------"+port);
        return port;
    }
    @Override
    public byte[] getResponse(byte[] bytes,byte[] connect){
        for (int i = 4; i <= 9; i++) {
            connect[i] = bytes[i];
        }
        System.out.println("response------------"+connect.toString());
        return connect;
    }
}
