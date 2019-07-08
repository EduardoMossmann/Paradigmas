package com.mycompany.mavenjsonserialization;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class ThreadGson extends Thread {

    String urlstr = "";
    List<String> nCommitsList;
    List<String> avgLengthList;
    public ThreadGson(String urlstr, List<String> nCommitsList, List<String> avgLengthList){
        this.urlstr = urlstr;
        this.nCommitsList = nCommitsList;
        this.avgLengthList = avgLengthList;
    }
    
    public void run() {
        try {
            URL url = new URL(urlstr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            System.out.println("Response code: " + con.getResponseCode());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            System.out.println(con.getHeaderFields().get("Link").get(0));

            JsonParser parser = new JsonParser();
            JsonArray results = parser.parse(in.readLine()).getAsJsonArray();
            int size = results.size();
            int messageTotalSize = 0;
            String message;
            for (JsonElement e : results) {
                message = e.getAsJsonObject().get("commit")
                        .getAsJsonObject().get("message").getAsString();
                messageTotalSize += message.length();
            }
            
            nCommitsList.add(Integer.toString(size));
            avgLengthList.add(Integer.toString( (messageTotalSize / size ) ));
            in.close();
        } catch (IOException e) {
            System.out.println("Erro na requisição ao github");
        }

    }

}
