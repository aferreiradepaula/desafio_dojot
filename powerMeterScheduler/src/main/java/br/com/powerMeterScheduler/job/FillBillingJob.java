package br.com.powerMeterScheduler.job;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.powerMeterScheduler.util.Util;

/**
 *
 * @author Coding for food
 *
 * Classe responsavel pela tarefa de leitura do arquivo gerado no job PowerMeterJob.java 
 * e posterior envio dos dados para o device na plataforma dojot via servi�o
 */
public class FillBillingJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
	
        System.out.println("Executando o envio do arquivo para o CBIL");
       final int BUFFER_SIZE = 4096;
        
            String ftpUrl = "ftp://%s:%s@%s/%s";
            String host = "awsdsno25";
            String user = "cbill005";
            String pass = "usuario";
            String filePath = "C:/ENTRADAX3B.txt";
            LocalDateTime dateTime = LocalDateTime.now();
            String uploadPath = "/cbill/files/rat/batch/input/input_CBILL_"+ dateTime.toString()+ "_.txt";
     
            ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
            System.out.println("Upload URL: " + ftpUrl);
     
            try {
            
            	//1 - Ler arquivo cbillTotalizer.txt criado pelo Adriano
            	//2 - Ler arquivo last_sent_cbill.txt
            	//3 - Fazer o Delta          	
            	//4 - Enviar o resultado para o BILLING
            	//5 - Apagar o arquivo last_sent_cbill.txt
            	//6 - Recriar o aruivo last_cent_cbill.txt atualizado para o calculo do proximo envi           	
            	Double result = Util.computeCBILL();
            	
            	//7 - Aumentando o valor do numero em 4 casas pois o BILING entende 12345 como 1,2345
            	result = result*10000; 
            	
            	//7 - ENVIANDO PARA O BILLING            	
                URL url = new URL(ftpUrl);
                URLConnection conn = url.openConnection();
                OutputStream outputStream = conn.getOutputStream();
                FileInputStream inputStream = new FileInputStream(filePath);
         

                inputStream.close();
                
                
               byte[] buffer = new byte[BUFFER_SIZE];

               //String msg = generateCbillLayoyt("PREDIO_12", result.intValue());
                String msg = generateCbillLayoyt("201830", result.intValue());
                //buffer = msg.getBytes(Charset.forName("UTF-8"));
                buffer = msg.getBytes();
                outputStream.write(buffer);
                
                outputStream.close();
                
                System.out.println("\n\nArquivo enviado para o CBIL");     
                System.out.println("File uploaded\n\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }      
     
        
        private static String generateCbillLayoyt(String accessPoint, Integer quantity){
        	if (quantity == 0) quantity = 88888;
           	StringBuilder sbt = new StringBuilder();
           	LocalDateTime localDateTime = LocalDateTime.now();
           
           	DateTimeFormatter df = DateTimeFormatter.BASIC_ISO_DATE;
           	DateTimeFormatter dfT = DateTimeFormatter.ofPattern("HHmmss");
           	String time = dfT.format(localDateTime);
           	String date = df.format(localDateTime);
           	
       	
        	StringBuilder sb = new StringBuilder();
        	
        	String pto = "PTOACESSO" + accessPoint;
        	int count = 30 - pto.length();
        	String whiteSpaces = "";
        	while(count>0){
        		whiteSpaces +=" ";
        		count--;
        	}
        	//LINHA HEADER
        	String duplicity = "0";
        	sb.append("HDJT            DJT "+ date + time + duplicity + pto +  whiteSpaces + "\n");        	
        	
        	//LINHA M
        	int count2 = 40 - accessPoint.length();
        	String whiteSpaces2 = "";
        	while(count2>0){
        		whiteSpaces2 +=" ";
        		count2--;
        	}
        	        	
        	int count3 = 10 - quantity.toString().length();
        	String leftZeros = "";
        	while(count3>0){
        		leftZeros +="0";
        		count3--;
        	}
        	sb.append("M" + date + time + accessPoint + whiteSpaces2 + leftZeros + quantity +"\n");
        	
        	//LINHA TRAILER
        	sb.append("T000000000000001\n");

        	        	
        	
        	
        	return sb.toString();
        }
        public static void main(String[] args){
			FillBillingJob job = new FillBillingJob();
			try {
				job.execute(null);
			} catch (JobExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}


