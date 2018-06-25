package br.com.powerMeterScheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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

        String fileContent = Util.fileToString("c:\\\\cck7550s_12.pmd");
        
        for(String parametro: fileContent.split(";")){
        	
        	String paramName  = parametro.split(":")[0];
        	String paramValue = parametro.split(":")[1];
        	
        	//paraName = o nome do parametro neste caso é o registrador que devera estar igualmente representado no device do dojot
        	//paramValue = o valor lido do registrador no codigo do Wilton
			
        	// TODO: montar arquivo para enviar via serviço
		}
        
    }
}
