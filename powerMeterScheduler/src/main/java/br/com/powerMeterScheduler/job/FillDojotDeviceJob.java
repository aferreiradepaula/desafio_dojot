package br.com.powerMeterScheduler.job;

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
        	}
        	
        	MQTTProtocolManagement  mqtt = new MQTTProtocolManagement();
        	mqtt.sendMessage(jsonMsg);
        	// apaga o arquivo depois de enviar.
        	Util.deleteFile();
        }
    }
}
