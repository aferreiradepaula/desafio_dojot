package br.com.powerMeterScheduler.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Util {

	private static final ResourceBundle deviceConfig = ResourceBundle.getBundle("device_config");
	
	public static final String URL_BROKER = "url_broker";
	public static final String DEVICE_ID = "device_id";
	public static final String CLIENT_ID = "client_id";
	public static final String PASSWD = "password";
	public static final String FILE_CONTENT_PATH = "file_content_path";
	public static final String FILE_CONTENT_NAME = "file_content_name";
	public static final String FILE_CBILL_CONTENT_PATH = "file_cbill_content_path";
	public static final String FILE_CBILL_CONTENT_NAME = "file_cbill_content_name";
	public static final String FILE_LAST_SENT_CBILL_CONTENT_PATH = "file_last_sent_cbill_content_path";
	public static final String FILE_LAST_SENT_CBILL_CONTENT_NAME = "file_last_sent_cbill_content_name";
	public static final String POWER_METER_REPETIION_TIME = "power_meter_repetition_time";
	public static final String FILL_DOJOT_DEVICE_REPETITION_TIME = "fill_dojot_device_repetition_time";
	public static final String ELAPSE_TIME_BETWEEN_PROCESS = "elapse_time_between_process";
	public static final String DEVICE_READER_COMMAND = "device_reader_command";
	private static final Path filePath = Paths.get(getConfig(Util.FILE_CONTENT_PATH)+getConfig(Util.FILE_CONTENT_NAME));
	private static final Path fileCbillPath = Paths.get(getConfig(Util.FILE_CBILL_CONTENT_PATH)+getConfig(Util.FILE_CBILL_CONTENT_NAME));
	private static final Path fileLastSentCbillPath = Paths.get(getConfig(Util.FILE_LAST_SENT_CBILL_CONTENT_PATH)+getConfig(Util.FILE_LAST_SENT_CBILL_CONTENT_NAME));
	
	
	/**
	 * Recupera os parametros do arquivo de configuracao.
	 * @param configName
	 * @return
	 */
	public static String getConfig(String configName) {
		return deviceConfig.getString(configName).trim();
	}

	/**
	 * Metodo responsavel por executar a chamada do leitor do medidor em linguagem C.
	 * @param shellCommand
	 * @return
	 */
	public static String executeCommand(String shellCommand) {
		
		String output = new String();
		
		try {
			Process process;
			
			process = Runtime.getRuntime().exec(shellCommand);
			
			process.waitFor();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			
			for (String line; (line = reader.readLine()) != null; output += line);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return output;
	}
	
	public static void createCbillOutput(String paramValue) {
		

				boolean exists = Files.exists(fileCbillPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
				
				if (exists) {
					deleteFile(filePath);	
				}
				
				try {
					FileWriter arq = new FileWriter(fileCbillPath.toString());
				    PrintWriter gravarArq = new PrintWriter(arq);
					 
				    gravarArq.printf("%s", paramValue);
				    arq.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	}
	
	public static void createLastSentCbill(String paramValue) {
		
		boolean exists = Files.exists(fileLastSentCbillPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
		
		if (exists) {
			deleteFile(fileLastSentCbillPath);	
		}
		
		try {
			FileWriter arq = new FileWriter(fileLastSentCbillPath.toString());
		    PrintWriter gravarArq = new PrintWriter(arq);
			 
		    gravarArq.printf("%s", paramValue);
		    arq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

}
	
	public static String fileToString() {
		
	   StringBuilder stringBuilder = new StringBuilder();
		
		try	{
			boolean exists = Files.exists(filePath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
			
			if (exists) {			
				List<Object>  linhas = Arrays.asList(Files.lines( filePath, StandardCharsets.UTF_8).toArray());

				//Se quiser quebra de linha entre as linhas assim 
				//como no arquivo, incluir .append("\n") na linha abaixo
				for (Object object : linhas) {
					stringBuilder.append(object.toString().trim());
					stringBuilder.append(";");
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}
	
	/**
	 * Faz a leitura do medidor enviado para a DOJOT
	 * Faz a leitura do ultimo numero enviado para a DOJOT
	 * Calcula a diferenca para descobrir o consumo no periodo
	 * Envia o resultado acima para o faturamento do BILLING
	 * 
	 * @return
	 */
	public static Double computeCBILL() {
		
		   Double result = new Double(0.0);
			
			try	{
				boolean exists = Files.exists(fileCbillPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
				
				if (exists) {			
					List<Object>  linhas = Arrays.asList(Files.lines( fileCbillPath, StandardCharsets.UTF_8).toArray());

					//Se quiser quebra de linha entre as linhas assim 
					//como no arquivo, incluir .append("\n") na linha abaixo
						String ultimaLeitura = linhas.get(0).toString().trim();
						Double ultima = Double.parseDouble(ultimaLeitura);
					boolean existsLastCbill = Files.exists(fileLastSentCbillPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
					if (existsLastCbill) {			
						List<Object>  linhasLastCbill = Arrays.asList(Files.lines(fileLastSentCbillPath, StandardCharsets.UTF_8).toArray());
						String penultimaLeitura = linhasLastCbill.get(0).toString().trim();
						Double penultima = Double.parseDouble(penultimaLeitura);
						result = ultima - penultima;
						
						//apagar o arquivo do penultimo envio
						//recria-lo com o valor atualizado
						Util.createLastSentCbill(ultimaLeitura);
						
						return result;
						
				}else{
					//Se o arquivo da penultimaLeitura ainda nao existe é pq eh o primeiro envio para o Billing
					//Neste caso enviaremos o valor 0 (ZERO) para nao comprometer o faturamento do cliente
					Util.createLastSentCbill(ultimaLeitura);
					return 0.0;
				}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}
	
	/**
	 * Apagador de arquivos generico
	 * @param path
	 */
	public static void deleteFile(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Apaga o arquivo filePath gerado a partir da leitura do medidor
	 */
	public static void deleteFile() {
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean gravarArquivo(String filePath, String content) {
		
		PrintWriter output = null;
		
		try {
			
			output = new PrintWriter(filePath);
			
			output.write(content);
			
			output.close();
			
			return true;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(output!=null) {
				output.close();
			}
		}
		
		return false;
	}
	
	
	
}
