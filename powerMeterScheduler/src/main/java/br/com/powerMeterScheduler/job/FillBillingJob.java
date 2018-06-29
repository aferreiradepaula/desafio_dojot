package br.com.powerMeterScheduler.job;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.FileWriter;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.powerMeterScheduler.device.ModbusConstantsMap;
import br.com.powerMeterScheduler.protocols.MQTTProtocolManagement;
import br.com.powerMeterScheduler.util.Util;

/**
 *
 * @author Coding for food
 *
 * Classe responsavel pela tarefa de leitura do arquivo gerado no job PowerMeterJob.java 
 * e posterior envio dos dados para o device na plataforma dojot via serviço
 */
public class FillBillingJob{ //implements Job {

	//public void execute(JobExecutionContext context) throws JobExecutionException {
	
        //System.out.println("Executando o envio do arquivo para o CBIL");
        private static final int BUFFER_SIZE = 4096;
        
        public static void main(String[] args) {
            String ftpUrl = "ftp://%s:%s@%s/%s";
            String host = "awsdsno25";
            String user = "cbill005";
            String pass = "usuario";
            String filePath = "C:/ENTRADA9";
            String uploadPath = "/cbill/files/rat/batch/input/ENTRADAX3.txt";
     
            ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
            System.out.println("Upload URL: " + ftpUrl);
     
            try {
                URL url = new URL(ftpUrl);
                URLConnection conn = url.openConnection();
                OutputStream outputStream = conn.getOutputStream();
                FileInputStream inputStream = new FileInputStream(filePath);
     
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
     

                inputStream.close();
                outputStream.close();
     
                System.out.println("File uploaded");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }      
        
    }
//}
