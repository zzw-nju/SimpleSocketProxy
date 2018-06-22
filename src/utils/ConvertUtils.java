package utils;

import pojo.AuthenticationRequestEntity;
import pojo.AuthenticationResponseEntity;
import pojo.TransmitRequestEntity;
import pojo.TransmitResponseEntity;

public class ConvertUtils {
    //byte数组转换为十六进制的字符串
    public String bytesToHexString(byte[] bytes, int begin, int end) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = begin; i < end; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }
    //byte数组转换为pojo
    public Object bytesToPojo(Object obj,byte[] bytes) {
        if(obj instanceof AuthenticationRequestEntity){
            ((AuthenticationRequestEntity) obj).setVer(bytes[0]);
            ((AuthenticationRequestEntity) obj).setNmethods(bytes[1]);
            ((AuthenticationRequestEntity) obj).setMethods(bytes[2]);
        }else if(obj instanceof AuthenticationResponseEntity){
            ((AuthenticationResponseEntity) obj).setVer(bytes[0]);
            ((AuthenticationResponseEntity) obj).setMethod(bytes[1]);
        }else if(obj instanceof TransmitRequestEntity){

        }else if(obj instanceof TransmitResponseEntity){

        }
        return obj;
    }
    //pojo转换为byte数组
    public <T> byte[] pojoTobytes(T x) {
        return null;
    }
}
