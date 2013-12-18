package fengfei.fir.service;

import java.util.Map;

public interface JpegExifWriter extends JpegExif {

    public final static String KeyIdUser = "idUser";
    public final static String KeyIdPhoto = "id_photo";
    public final static String KeyId = "id";
    public final static String KeyResult = "result";

    Map<String, Object> writeExif(
        String imageFile,
        Map<String, String> exifs,
        Map<String, String> contents) throws Exception;

    Map<String, Object> updateExif(
        Map<String, String> contents) throws Exception;

    public Map<String, Object> writePhoto(
        String imageFile,
        Map<String, String> values,
        Map<String, String> contents) throws Exception;
}
