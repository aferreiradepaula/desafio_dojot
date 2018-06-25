package br.com.powerMeterScheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.powerMeterScheduler.util.Util;

/**
*
* @author Coding for food
*
* Classe responsavel por realizar a leitura dos dados do medidor e gravar essas informações em arquivo em disco
*/
public class PowerMeterJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Executando a leitura do equipamentoo...");

        //Executando comando que executa o leitor feito em C pelo Wilton
        String output = Util.executeCommand("cmd /c ping www.uol.com.br");
        
    }

}
