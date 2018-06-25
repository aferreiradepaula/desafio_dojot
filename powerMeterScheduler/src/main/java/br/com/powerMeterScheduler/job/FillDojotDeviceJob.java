package br.com.powerMeterScheduler.job;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
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

        String fileContent = Util.fileToString("D:\\\\desenvolvimento\\\\Work\\\\Repositorios\\\\GitHub\\\\desafio_dojot\\\\cck7550s_12.pmd");
        
        System.out.println("Conteudo do arquivo: "+fileContent);
        
        JSONObject jsonMsg;
        
        if (fileContent != null && !fileContent.equals("")) {
        	
        	jsonMsg = new JSONObject();
        	for(String parametro: fileContent.split(";")){

        		String paramName  = parametro.split(":")[0];
        		String paramValue = parametro.split(":")[1];

        		jsonMsg.put(paramName, paramValue);
        	}

        	String topic        = "/equipe03/3dec08/attrs";
        	int qos             = 2;
        	String broker       = "tcp://desafio-dojot-iot.cpqd.com.br:1883";
        	String clientId     = "equipe03";
        	String passWord     = "DhZ5rd6WTp4S";
        	MemoryPersistence persistence = new MemoryPersistence();

        	try {
        		MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        		MqttConnectOptions connOpts = new MqttConnectOptions();        		
        		connOpts.setCleanSession(true);
        		connOpts.setUserName(clientId);
        		connOpts.setPassword(passWord.toCharArray());
        		
        		System.out.println("Connecting to broker: "+broker);
        		sampleClient.connect(connOpts);
        		System.out.println("Connected");
        		System.out.println("Publishing message: "+jsonMsg);
        		MqttMessage message = new MqttMessage(jsonMsg.toString().getBytes());
        		message.setQos(qos);
        		sampleClient.publish(topic, message);
        		System.out.println("Message published");
        		sampleClient.disconnect();
        		System.out.println("Disconnected");
        		System.exit(0);
        	} catch(MqttException me) {
        		System.out.println("reason "+me.getReasonCode());
        		System.out.println("msg "+me.getMessage());
        		System.out.println("loc "+me.getLocalizedMessage());
        		System.out.println("cause "+me.getCause());
        		System.out.println("excep "+me);
        		me.printStackTrace();
        	}

        }
    }
}
