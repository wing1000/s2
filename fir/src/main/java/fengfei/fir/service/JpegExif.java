package fengfei.fir.service;

public interface JpegExif {

    public final static String Make = "Make";
    public final static String Model = "Model";
    public final static String FNumber = "FNumber";
    public final static String ShutterSpeedValue = "ShutterSpeedValue";
    public final static String ISO = "iso";
    public final static String LensModel = "LensModel";
    public final static String Lens = "Lens";
    public final static String FocalLength = "FocalLength";
    public final static String ExposureTime = "ExposureTime";
    public final static String ExposureCompensation = "ExposureCompensation";
    public final static String DateTimeOriginal = "DateTimeOriginal";

    public final static String AllTags[] = { Make, Model, FNumber, ShutterSpeedValue, ISO, //
            LensModel, Lens, FocalLength, ExposureTime, ExposureCompensation, DateTimeOriginal };

}
