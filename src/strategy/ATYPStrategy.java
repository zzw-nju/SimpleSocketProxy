package strategy;

public interface ATYPStrategy {
    public String getHost(byte[] bytes);
    public int getPort(byte[] bytes);
    public byte[] getResponse(byte[] bytes,byte[] connect);
}
