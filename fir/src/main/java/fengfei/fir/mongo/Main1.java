package fengfei.fir.mongo;

import com.google.code.fqueue.FQueue;

/**
 */
public class Main1 {
    public static void main(String[] args) throws Exception {
        FQueue fQueue = new FQueue("/home/db");
        StringBuilder sb = new StringBuilder();
        int length = 1024;
        for (int i = 0; i < length; i++) {
            sb.append("a");
        }
        byte[] data = sb.toString().getBytes();
        fQueue.add(data);// 预热一下
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            fQueue.add(data);
        }
        System.out.println(1000000 / ((System.currentTimeMillis() - start) / 1000) + "qps");
        fQueue.close();
    }
}
