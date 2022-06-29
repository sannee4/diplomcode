package com.urrsunn.visitstat.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileRead {
    private final Map<String, List<String>> livingCityMap = new HashMap<>();

    {
        FileInputStream fstream;
        try {
            fstream = new FileInputStream("src/main/resources/dict_addr2.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int i = 0;
        //Read File Line By Line
        while (true) {
            i++;
            try {
                if ((strLine = br.readLine()) == null) break;
                try {
                    String[] arr = strLine.split("; ");
                    if (livingCityMap.containsKey(arr[1])) {
                        livingCityMap.get(arr[1]).add(arr[0]);
                    } else
                        livingCityMap.put(arr[1], List.of(arr[0]));
                    if (i % 10000 == 0) {
                        System.out.println("строк " + i);
                        System.out.println(i);
                    }
                } catch (Exception e) {

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<String, List<String>> getMap() {
        return livingCityMap;
    }

}