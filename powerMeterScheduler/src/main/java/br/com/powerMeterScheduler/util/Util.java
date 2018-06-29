package br.com.powerMeterScheduler.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
	private static final Path filePath = Paths.get(getConfig(Util.FILE_CONTENT_PATH)+getConfig(Util.FILE_CONTENT_NAME));
	
	
	/**
	 * Recupera os parametros do arquivo de configuracao.
	 * @param configName
	 * @return
	 */
	public static String getConfig(String configName) {
		return deviceConfig.getString(configName);
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
	
	
	public static String fileToString() {
		
	   StringBuilder stringBuilder = new StringBuilder();
		
		try	{
			List<Object>  linhas = Arrays.asList(Files.lines( filePath, StandardCharsets.UTF_8).toArray());
			
			//Se quiser quebra de linha entre as linhas assim 
			//como no arquivo, incluir .append("\n") na linha abaixo
			for (Object object : linhas) {
				stringBuilder.append(object.toString().trim());
				stringBuilder.append(";");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}
	
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
