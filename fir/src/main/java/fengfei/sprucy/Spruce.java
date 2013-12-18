package fengfei.sprucy;

import fengfei.fir.service.JpegProcess;
import fengfei.fir.service.LorryStorage;
import fengfei.fir.service.impl.DefaultJpegProcess;
import fengfei.fir.service.impl.DiskLorryStorage;

import java.util.HashMap;
import java.util.Map;

public class Spruce {

    static LorryStorage lorryStorage = new DiskLorryStorage();
    static JpegProcess jpegProcess = new DefaultJpegProcess();
    static int uploadMaxFolder = 32;
    static String uploadRoot = "./upload";
    static int cropMaxWidth = 1024;
    static int cropMaxHeight = 1024;
    static int previewNumber = 2;
    static Map<Integer, int[]> previewDimensions = new HashMap<>();

    static {
        previewDimensions.put(1, new int[]{300, 300});
        previewDimensions.put(2, new int[]{160, 160});
        previewDimensions.put(3, new int[]{90, 90});
    }

    public static String getUploadRoot() {
        return uploadRoot;
    }

    public static LorryStorage getLorryStorage() {
        return lorryStorage;
    }

    public static JpegProcess getJpegProcess() {
        return jpegProcess;
    }

    // public static JpegProcess newJpegProcess() {
    // return SpruceInit.getJpegProcess();
    // }
    //
    // public static LorryStorage newLorryStorage() {
    // return SpruceInit.getLorryStorage();
    // }

    public static int getUploadMaxFolder() {
        return uploadMaxFolder;
    }

    public static int getCropMaxWidth() {
        return cropMaxWidth;
    }

    public static int getCropMaxHeight() {
        return cropMaxHeight;
    }

    public static int getPreviewNumber() {
        return previewNumber;
    }

    public static Map<Integer, int[]> getPreviewDimensions() {
        return previewDimensions;
    }

}
