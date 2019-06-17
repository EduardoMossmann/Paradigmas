package principal;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Boolean;

public class RandomPickerCmd {

    
        
    public static void main(String[] args) {
        List<String> list = new ArrayList<String> ();
        try {
        FileReader arq = new FileReader("names.txt");
        BufferedReader lerArq = new BufferedReader(arq);
        
        String linha = lerArq.readLine();
        
        while (linha != null) {
          list.add(linha);
          
          linha = lerArq.readLine(); 
        }

        arq.close();
      } catch (IOException erro) {
          System.err.printf("Erro na abertura do arquivo");
       }
        
        boolean Online = ChooseOnlineOrOffline(list);
        if(Online)
            RandomPickerOnline(list);
        else
            RandomPickerOffline(list);
        
    }
    
    public static boolean ChooseOnlineOrOffline(List<String> list){
        int tam = list.size();
        try{
        String urlstr = "https://www.random.org/lists/?mode=advanced";
        URL url = new URL(urlstr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        }catch(IOException error){
            return false;
        }
        return tam < 20;
        // MAIS DE 20 LINHAS, NÃO HÁ REQUISIÇÃO ONLINE
    }
    
    public static void RandomPickerOnline(List<String> list){
         try {
        String urlstr = "https://www.random.org/lists/?mode=advanced";
        URL url = new URL(urlstr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setDoOutput(true);

        String data = "list=";
        for(int i = 0; i < list.size(); i++){
            data = data + list.get(i);
            if(i + 1 != list.size())
                data = data + "%0D%0A";
        }

        con.getOutputStream().write(data.getBytes("UTF-8"));
        System.out.println("Response code: " + con.getResponseCode());

        BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));

        String responseLine;
        StringBuffer response = new StringBuffer();
        while ((responseLine = in.readLine()) != null) {
          response.append(responseLine + "\n");
        }
        System.out.println(response); 

        in.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
    public static void RandomPickerOffline(List<String> list){
        boolean usado[] = new boolean[list.size()];
        for(int i = 0; i < list.size(); i++){
            usado[i] = false;
        }
        boolean naoAchou;
        Random rand = new Random();
        int idx;
        System.out.println("Aperte ENTER entre as linhas");
        for(int i = 0; i < list.size(); i++){
            naoAchou = true;
            while(naoAchou){
                idx = rand.nextInt(list.size() - 1);
                if(usado[idx] == false){
                    usado[idx] = true;
                    System.out.println(list.get(idx));
                    
                    naoAchou = false;
                }
            }   
        }
    }
}
