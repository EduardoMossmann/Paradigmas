package com.mycompany.githubanalyzercmd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GitHubAnalyzerCmd{

    static List<String> gitURLList = new ArrayList<>();
    
    public static void openFile(File file) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(file.getAbsolutePath()));

        String readString;
        while ((readString = in.readLine()) != null) {
            gitURLList.add(readString);
        }
    }

    public static void requestUrl(String urlS) throws Exception{
        URL url = new URL(urlS);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));


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
        
        System.out.println("Repositorio: " + urlS);
        System.out.println("Numero de commits:" + size);
        System.out.println("Tamanho medio: " + messageTotalSize/size);
        in.close();
    }

    public static void requestToGithub() throws Exception{
        int cont = 0;
        for (String s : gitURLList) {
            requestUrl(s);
        }
    }
    public static void loadFile(String fileString) throws Exception{
        File file = new File(fileString);
        if (file != null) {
            openFile(file);
            requestToGithub();
        } else {
            System.out.println("Arquivo não existe, fim de execucao");
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.println("Insira o nome do arquivo (Ex: Arquivo.txt -- O arquivo"
                + " deve estar na pasta)");
        String fileString = reader.nextLine();
        loadFile(fileString);
    }
}

