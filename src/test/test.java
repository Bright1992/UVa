package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class test {
    public static void main(String []argv){
        Set<Integer> s = new HashSet<>();
        Map<Set<Integer>, Integer> m = new HashMap<>();
        s.add(1);
        m.put(s,0);
        s=new HashSet<>();
        s.add(1);
        m.put(s,1);
        for(Map.Entry<Set<Integer>, Integer> e: m.entrySet()){
            System.out.println(e.getKey());
        }
    }
}
