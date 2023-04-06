package com.iot.rashome.commons.exception;

public class IotBackendException extends Exception {
    
    public IotBackendException(){
        super();
    }

    public IotBackendException(String message){
        super(message);
    }

    public IotBackendException(Throwable e){
        super(e);
    }

    public IotBackendException(String message, Throwable e){
        super(message, e);
    }

    public static IotBackendException parametersInMessage(String message, Object... objects){
        return new IotBackendException(String.format(message, objects));
    }

    public static IotBackendException deviceIsNotRegist() {
        return new IotBackendException("设备未注册");
    }


    public static IotBackendException nullParameters(String objectName){
        return new IotBackendException(String.format("%s 是 null", objectName));
    }

    public static IotBackendException nullParameters(String... objectNames){

        StringBuilder stringBuilder = new StringBuilder();

        for (String objectName : objectNames) {
            stringBuilder.append(objectName + ", ");
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-2);

        stringBuilder.append("其中一个是 null, 或者全部是 null");

        return new IotBackendException(stringBuilder.toString());
    }
}
