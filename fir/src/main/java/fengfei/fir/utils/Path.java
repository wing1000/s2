package fengfei.fir.utils;

import java.io.File;

public class Path {

    public static String DOWNLOAD_PATH = "/photo";
    public static String UPLOAD_PATH = "./photo";
    public static int MaxFolder = 1024;

    public static String getHeadPhotoDownloadPath(long id) {
        StringBuilder sb = Path.getPath(DOWNLOAD_PATH + "/users", id);
        sb.append("/");
        sb.append(0);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getHeadPhotoDownloadPath(long id, int preview) {
        StringBuilder sb = Path.getPath(DOWNLOAD_PATH + "/users", id);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getHeadPhotoUploadPath(long id) {
        StringBuilder sb = Path.getPath(UPLOAD_PATH + "/users", id);
        sb.append("/");
        sb.append(0);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getHeadPhotoUploadPath(long id, int preview) {
        StringBuilder sb = Path.getPath(UPLOAD_PATH + "/users", id);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getJpegDownloadPath(long id, int preview) {
        StringBuilder sb = Path.getPath(DOWNLOAD_PATH, id);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getJpegDownloadPath(String id, int preview) {
        StringBuilder sb = Path.getPath(DOWNLOAD_PATH, id);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getJpegUploadPath(String id, int preview) {
        StringBuilder sb = Path.getPath(UPLOAD_PATH, id);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getJpegUploadPath(long id, int preview) {
        StringBuilder sb = Path.getPath(UPLOAD_PATH, id);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        return sb.toString();
    }

    public static String getUploadPath(String id) {
        StringBuilder sb = Path.getPath(UPLOAD_PATH, id);

        return sb.toString();
    }

    public static String getUploadPath(long id) {
        StringBuilder sb = Path.getPath(UPLOAD_PATH, id);

        return sb.toString();
    }

    public static String getDownloadPath(long id) {
        StringBuilder sb = Path.getPath(DOWNLOAD_PATH, id);
        return sb.toString();
    }

    public static String getDownloadPath(String id) {
        StringBuilder sb = Path.getPath(DOWNLOAD_PATH, id);
        return sb.toString();
    }

    public static String getPath(String root, String id, String extName) {
        StringBuilder sb = new StringBuilder(root);
        sb.append("/");
        String path1 = hashDiv(id);
        sb.append(path1).append("/");
        sb.append(hashDiv(path1)).append("/");
        sb.append(hashDiv(path1 + id)).append("/");

        sb.append(id + extName);
        return sb.toString();
    }

    public static String getPath(String root, long id, String name) {
        StringBuilder sb = new StringBuilder(root);
        sb.append("/");
        String path1 = hashDiv(id);
        sb.append(path1).append("/");
        sb.append(hashDiv(path1)).append("/");
        sb.append(id).append("/");
        sb.append(name);
        return sb.toString();
    }

    public static FilePath randomPath(String root) {
        String uuid = UUID.randomB32UUID();
        long id = Hash.murmurHash(uuid);
        String path = Path.getPath(root, id).toString();
        File file = new File(path);
        FilePath filePath = new FilePath(id, path);

        if (file.exists()) {
            filePath = randomPath(root);
        } else {
            return filePath;
        }
        return filePath;
    }

    public static StringBuilder getPath(String root, String id) {
        StringBuilder sb = new StringBuilder(root);
        sb.append("/");
        String path1 = hashDiv(id);
        sb.append(path1).append("/");
        sb.append(hashDiv(path1)).append("/");
        sb.append(id).append("/");

        return sb;
    }

    public static StringBuilder getPath(String root, long id) {
        StringBuilder sb = new StringBuilder(root);
        sb.append("/");
        String path1 = hashDiv(id);
        sb.append(path1).append("/");
        sb.append(hashDiv(path1)).append("/");
        sb.append(id).append("/");

        return sb;
    }

    public static String getPathBySplit(String root, String id, String extName) {
        StringBuilder sb = new StringBuilder(root);
        sb.append("/");
        String path1 = hashDiv(id);
        sb.append(path1).append("/");
        sb.append(hashDiv(path1)).append("/");
        sb.append(hashDiv(path1 + id)).append("/");
        sb.append(id + extName);
        return sb.toString();
    }

    public static String hashDiv(String str) {
        return hashDiv(str, MaxFolder);
    }

    public static String hashDiv(String str, int max) {
        long hash = Hash.murmurHash(str);
        long div = hash % max;
        return Long.toString(div < 0 ? -div : div, 32);
    }

    public static String hashDiv(long val) {
        return hashDiv(val, MaxFolder);
    }

    public static String hashDiv(long val, int max) {
        long hash = Hash.fnvHash64(val);
        long div = hash % max;
        return Long.toString(div < 0 ? -div : div, 32);
    }

    public static class FilePath {

        long id;
        String path;

        public FilePath(long id, String path) {
            super();
            this.id = id;
            this.path = path;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public String getFilePath(String name) {
            return path + name;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "FilePath [id=" + id + ", path=" + path + "]";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            FilePath path = randomPath(UPLOAD_PATH);
            System.out.println(path);

        }
    }
}
