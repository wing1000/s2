package fengfei.fir.service;

public class JpegFile {

    private String path;
    private int width;
    private int height;
    private boolean isSetSize = false;

    public JpegFile(String path, int width, int height) {
        super();
        this.path = path;
        this.width = width;
        this.height = height;
        if (width > 0 && height > 0) {
            isSetSize = true;
        }
    }

    public JpegFile(String path) {
        super();
        this.path = path;

    }

    public JpegFile() {
    }

    public boolean isSetSize() {
        return (width > 0 && height > 0) ? true : false;

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
