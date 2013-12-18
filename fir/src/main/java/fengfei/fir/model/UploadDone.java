package fengfei.fir.model;

import java.util.Map;

public class UploadDone extends Done {

    final static String KeyIndex = "index";
    final static String KeyId = "id";
    final static String KeyPath = "path";
    final static String KeyExif = "exif";

    public UploadDone(int index, String id, String path) {
        super();
        setId(id);
        setIndex(index);
        setPath(path);
    }

    public UploadDone(int index, String id) {
        super();
        setId(id);
        setIndex(index);

    }

    public String getPath() {
        return (String) get(KeyPath);
    }

    public void setPath(String path) {
        put(KeyPath, path);

    }

    public int getIndex() {
        return (Integer) get(KeyIndex);
    }

    public void setIndex(int index) {
        put(KeyIndex, index);
    }

    public String getId() {
        return (String) get(KeyId);
    }

    public void setId(String id) {
        put(KeyId, id);
    }

    public Map<String, String> getExif() {
        return (Map<String, String>) get(KeyExif);
    }

    public void setExif(Map<String, String> exif) {
        put(KeyExif, exif);
    }

}
