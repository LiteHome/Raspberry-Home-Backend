package com.rashome.rashome.utils;

public class BytesConvert {

    public static float convertToKB(float size){
        return size / 1024;
    }
    
    public static float convertToMB(float size){
        return size / 1048576;
    }

    public static float convertToGB(float size){
        return size / 1073741824;
    }
}