package fengfei.ucm.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import fengfei.forest.slice.Equalizer;
import fengfei.forest.slice.Range;
import fengfei.forest.slice.SliceResource.Function;

public class LoopRoundEqualizer implements Equalizer<Long> {

    private long moduleSize = 1000;

    public LoopRoundEqualizer() {
    }

    public LoopRoundEqualizer(long moduleSize) {
        super();
        this.moduleSize = moduleSize;
    }

    public long getModuleSize() {
        return moduleSize;
    }

    @Override
    public long get(Long key, int sliceSize) {
        long index = Math.abs(key % moduleSize);

        return index == 0 ? moduleSize : index;
    }

    public static void main(String[] args) {
        int size = 10;

        MultiMap map = new MultiValueMap();
        Random random = new Random();
        LoopRoundEqualizer e = new LoopRoundEqualizer(10);

        for (int i = 1; i <= 100; i++) {
            long key = i;// + 1024;
            // key = random.nextLong();
            long index = e.get(key, size);
            map.put(index, key);
            System.out.println(i + " : " + index);
            // System.out.println(i + " : " + e.get(random.nextLong(), 3));
        }
        Set<Entry> set = map.entrySet();
        for (Entry entry : set) {
            List list = (List) entry.getValue();
            System.out.println(entry);
            // System.out.println(entry.getKey() + "=" + list.size());
        }

    }

}
