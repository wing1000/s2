package fengfei.fir.utils;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import fengfei.fir.model.PhotoRank;

import java.io.ByteArrayOutputStream;

public class Kryor {

    static com.esotericsoftware.kryo.Kryo kryo = new com.esotericsoftware.kryo.Kryo();

    public static byte[] write(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        Output output = new Output(out);
        kryo.writeObject(output, obj);
        byte[] bs = output.toBytes();
        output.close();
        return bs;
    }

    public static <T> T read(byte[] bs, Class<T> clazz) {
        Input input = new Input(bs);
        T t = kryo.readObject(input, clazz);
        input.close();
        return t;
    }

    public static void main(String[] args) {
        PhotoRank photoRank = new PhotoRank();
        photoRank.idPhoto = 1;
        photoRank.category = 1;
        photoRank.title = "title1";
        photoRank.idUser = 24;
        photoRank.niceName = "userNmae";
        photoRank.score = 83.32d;

        byte[] bs = write(photoRank);
        System.out.println(bs.length);

        System.out.println(photoRank);
        // ...

        PhotoRank rank = read(bs, PhotoRank.class);
        System.out.println(rank);

    }
}
