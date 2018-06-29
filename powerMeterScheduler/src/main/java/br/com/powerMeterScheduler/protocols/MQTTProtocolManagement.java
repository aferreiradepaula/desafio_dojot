package br.com.powerMeterScheduler.protocols;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

import br.com.powerMeterScheduler.util.Util;

public class MQTTProtocolManagement {
	
	private String clientId     = Util.getConfig(Util.CLIENT_ID);
	private String deviceId     = Util.getConfig(Util.DEVICE_ID);
	private String topic        = "/"+clientId+"/"+deviceId+"/attrs";
	private int    qos          = 2;
	private String broker       = Util.getConfig(Util.URL_BROKER);	
	private String passWd       = Util.getConfig(Util.PASSWD);
	
	MqttClient sampleClient;
	MqttConnectOptions connOpts;
	
	MemoryPersistence persistence = new MemoryPersistence();
	
	public MQTTProtocolManagement() {
		super();
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
		} catch (MqttException e) {
			handleException(e);	
		}
		connOpts = new MqttConnectOptions();        		
		connOpts.setCleanSession(true);
		connOpts.setUserName(clientId);
		connOpts.setPassword(passWd.toCharArray());
		connect();
	}
	

	public void sendMessage(JSONObject jsonMsg) {
		try 
		{
			System.out.println("Enviando dados: "+jsonMsg);        		
			MqttMessage message = new MqttMessage(jsonMsg.toString().getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Enviado!");
			disconnect();
		} catch(MqttException e) {
			handleException(e);	
		}
	}
	
	
	private void connect () {
		try {
			sampleClient.connect(connOpts);		
		} catch (MqttException e) {
			handleException(e);			
		}
	}
	
	private void disconnect() {
		try {
			sampleClient.disconnect();
		} catch (MqttException e) {
			handleException(e);	
		}
	}
	
	
	private void handleException (MqttException me){
		System.out.println("reason "+me.getReasonCode());
		System.out.println("msg "+me.getMessage());
		System.out.println("loc "+me.getLocalizedMessage());
		System.out.println("cause "+me.getCause());
		System.out.println("excep "+me);
		me.printStackTrace();
	}
	
	

}
