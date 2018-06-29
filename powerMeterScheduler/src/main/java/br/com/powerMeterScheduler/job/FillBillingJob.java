package br.com.powerMeterScheduler.job;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.io.FileWriter;
import java.io.ObjectInputStream.GetField;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mchange.v2.codegen.bean.GeneratorExtension;

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
            String filePath = "C:/ENTRADAX3B.txt";
            //String filePath = "ENTRADA1234.txt";
            
            String uploadPath = "/cbill/files/rat/batch/input/ENTRADA790.txt";
     
            ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
            System.out.println("Upload URL: " + ftpUrl);
     
            try {
                URL url = new URL(ftpUrl);
                URLConnection conn = url.openConnection();
                OutputStream outputStream = conn.getOutputStream();
                FileInputStream inputStream = new FileInputStream(filePath);
     
                //byte[] buffer = new byte[BUFFER_SIZE];
                //int bytesRead = -1;
                //while ((bytesRead = inputStream.read(buffer)) != -1) {
                //    outputStream.write(buffer, 0, bytesRead);
                //}
     

                inputStream.close();
                
                
               byte[] buffer = new byte[BUFFER_SIZE];
                String msg = generateCbillLayoyt("PREDIO_12", 89999);
                //buffer = msg.getBytes(Charset.forName("UTF-8"));
                buffer = msg.getBytes();
                outputStream.write(buffer);
                
                outputStream.close();
     
                System.out.println("File uploaded");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }      
     
        
        private static String generateCbillLayoyt(String accessPoint, Integer quantity){
        	StringBuilder sb = new StringBuilder("H            DJT DJT");
        	sb.append("20180629083045");
        	sb.append("0");
        	//sb.append("                     PREDIO_12"); //Ponto de acesso tem q ter taamanho 30
        	int count = 30 - accessPoint.length();
        	String whiteSpaces = "";
        	while(count>0){
        		whiteSpaces +=" ";
        		count--;
        	}
        	sb.append(whiteSpaces+accessPoint);
        	sb.append("\r\n");
        	sb.append("M");
        	sb.append("20180629102045");
        	int count2 = 40 - accessPoint.length();
        	String whiteSpaces2 = "";
        	while(count2>0){
        		whiteSpaces2 +=" ";
        		count2--;
        	}
        	//sb.append("                               PREDIO_12")
        	sb.append(whiteSpaces2+accessPoint);
        	//0000089999
        	int count3 = 10 - quantity.toString().length();
        	String whiteSpaces3 = "";
        	while(count3>0){
        		whiteSpaces3 +="0";
        		count3--;
        	}
        	sb.append(whiteSpaces3+quantity);
        	sb.append("\r\n");
        	sb.append("T");
        	sb.append("000000000000001");
        	sb.append("\r\n");
        	
        	
        	return sb.toString();
        }
    }
//}
