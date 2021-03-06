package br.com.codingforfood.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Util {

	
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
	
	
	public static String fileToString(String filePath) {
		
	   StringBuilder stringBuilder = new StringBuilder();
		
		try	{
			List<Object>  linhas = Arrays.asList(Files.lines( Paths.get(filePath), StandardCharsets.UTF_8).toArray());
			
			//Se quiser quebra de linha entre as linhas assim 
			//como no arquivo, incluir .append("\n") na linha abaixo
			for (Object object : linhas) {
				stringBuilder.append(object.toString());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
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
