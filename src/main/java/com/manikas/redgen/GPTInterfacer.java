package com.manikas.redgen;
import com.sun.jna.StringArray;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GPTInterfacer {
    public static String requestGPT(String gptIN){
        String apiKey = "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";
        String requestURL = "https://api.openai.com/v1/chat/completions";
        String aiType = "gpt-3.5-turbo";

        try {
            //Connect to API
            URL requestSite = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) requestSite.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorisation", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            //Request
            String requestParams = "{\"model\": \"" + aiType + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + gptIN + "\"}]}";
            connection.setDoOutput(true);
            //Out Code --> AI
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(requestParams);
            streamWriter.flush();
            streamWriter.close();

            BufferedReader gptOUT = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String OUTcurrentLine;
            StringBuffer gptOUTfull = new StringBuffer();
            while ((OUTcurrentLine = gptOUT.readLine()) != null){
                gptOUTfull.append(OUTcurrentLine);
            }
            gptOUT.close();

            return (gptOUTfull.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);

        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }

    //dummy function for later use - not functional
    public static String primeGPT(){
        String apiKey = "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";
        String requestURL = "https://api.openai.com/v1/chat/completions";
        String aiType = "gpt-3.5-turbo";

        String rolePrompt = "";

        try {
            //Connect to API
            URL requestSite = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) requestSite.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorisation", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            //Request
            String requestParams = "{\"model\": \"" + aiType + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + rolePrompt + "\"}]}";
            connection.setDoOutput(true);
            //Out Code --> AI
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(requestParams);
            streamWriter.flush();
            streamWriter.close();

            BufferedReader gptOUT = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String OUTcurrentLine;
            StringBuffer gptOUTfull = new StringBuffer();
            while ((OUTcurrentLine = gptOUT.readLine()) != null){
                gptOUTfull.append(OUTcurrentLine);
            }
            gptOUT.close();

            return (gptOUTfull.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);

        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }


    //dummy function for later use - non functional
    public static String feedData(StringArray dataset){
        String apiKey = "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";
        String requestURL = "https://api.openai.com/v1/chat/completions";
        String aiType = "gpt-3.5-turbo";

        try {
            //Connect to API
            URL requestSite = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) requestSite.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorisation", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            //Request
            String requestParams = "{\"model\": \"" + aiType + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + gptIN + "\"}]}";
            connection.setDoOutput(true);
            //Out Code --> AI
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(requestParams);
            streamWriter.flush();
            streamWriter.close();

            BufferedReader gptOUT = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String OUTcurrentLine;
            StringBuffer gptOUTfull = new StringBuffer();
            while ((OUTcurrentLine = gptOUT.readLine()) != null){
                gptOUTfull.append(OUTcurrentLine);
            }
            gptOUT.close();

            return (gptOUTfull.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);

        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }
}
