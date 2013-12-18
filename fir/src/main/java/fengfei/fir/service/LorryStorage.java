package fengfei.fir.service;

import fengfei.fir.model.UploadDone;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface LorryStorage {

    UploadDone writeFile(Map<String, String> exifs, Map<String, String> contents, File file)
        throws Exception;

    List<UploadDone> writeFile(InputStream... ins) throws Exception;

    void readFile(String fileName, OutputStream out) throws Exception;

    void readFile(String[] fileNames, OutputStream[] outs) throws Exception;
}
