package br.com.powerMeterScheduler.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;

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
public class FillDojotDeviceJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Executando a leitura e envio ao device do arquivo gerado pelo job PowerMeterJob.java...");
                
        String fileContent = Util.fileToString();
        JSONObject jsonMsg;
        
        if (fileContent != null && !fileContent.equals("")) {
        	
        	jsonMsg = new JSONObject();
        	for(String parametro: fileContent.split(";")){ 
        		String paramName  = parametro.split(":")[0].trim();
        		String paramValue = parametro.split(":")[1].trim();
        
        		jsonMsg.put(ModbusConstantsMap.stringFromCommand(Integer.parseInt(paramName)), paramValue);
        		System.out.println("jsonMsg: "+jsonMsg);
        		if(ModbusConstantsMap.TOTALIZADOR_MWH_POS_FASE_A.getModBusCommand().toString().equals(paramName)){
        			Util.createCbillOutput(paramValue);
        		}
        	}
        	
        	MQTTProtocolManagement  mqtt = new MQTTProtocolManagement();
        	mqtt.sendMessage(jsonMsg);
        	// apaga o arquivo depois de enviar.
        	Util.deleteFile();
        }
    }

		public static void main(String[] args){
			FillDojotDeviceJob job = new FillDojotDeviceJob();
			try {
				job.execute(null);
			} catch (JobExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

