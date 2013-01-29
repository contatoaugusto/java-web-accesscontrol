package cobra.controleacesso.web.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Antonio Augusto
 *
 * Classe que cria a entrada no arquivo XML, mais precisamente na entrada http, no �tem intercept-url.
 */
public class FileHandler {

	private static final Log log = LogFactory
			.getLog(FileHandler.class);
	
	public void criaInterceptUrlXML(String link, String filePathFileName) {
		
		if (link == null || link.trim().equals("")){
			//throw new Exception("N�o foi informado o nome da p�gina a ser inserida em intercept-url do xml");
			log.debug("N�o foi informado o nome da p�gina a ser inserida em intercept-url do xml");
			System.out.println("N�o foi informado o nome da p�gina a ser inserida em intercept-url do xml");
		}
		if (filePathFileName == null || filePathFileName.trim().equals("")){
			//throw new Exception("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
			log.debug("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
			System.out.println("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
		}
		
		try {

			File file = new File(filePathFileName);

			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String line = "";
			StringBuffer conteudoArquivo = new StringBuffer("");
			boolean hasLink = false;
			while((line = in.readLine()) != null){ 
				if (line.contains("</http>")){
					conteudoArquivo.append("		<intercept-url pattern=\"/" + link 
							+ "\" access=\"hasRole('"+ link +"')\"/>");
					conteudoArquivo.append(System.getProperty("line.separator"));
					hasLink = true;
				}
				conteudoArquivo.append(line);
				conteudoArquivo.append(System.getProperty("line.separator"));
			}
			in.close();
			
			if (hasLink){
				java.io.FileOutputStream fos = new java.io.FileOutputStream (file);   
				fos.write(conteudoArquivo.toString().getBytes());   
				fos.close();  
			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ filePathFileName +" n�o existe.");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro na leitura de " + filePathFileName +".");
			System.out.println(e.getMessage());
		}
	}
		
	/**
	 * Remove do arquivo a linha que cont�m a entrada igual a vari�vel link
	 * @param link
	 * @param filePathFileName
	 */
	public void removeInterceptUrlXML(String link, String filePathFileName) {
		
		if (link == null || link.trim().equals("")){
			//throw new Exception("N�o foi informado o nome da p�gina a ser inserida em intercept-url do xml");
			log.debug("N�o foi informado o nome da p�gina a ser inserida em intercept-url do xml");
			System.out.println("N�o foi informado o nome da p�gina a ser inserida em intercept-url do xml");
		}
		if (filePathFileName == null || filePathFileName.trim().equals("")){
			//throw new Exception("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
			log.debug("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
			System.out.println("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
		}
		
		try {

			File file = new File(filePathFileName);

			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String line = "";
			StringBuffer conteudoArquivo = new StringBuffer("");
			boolean hasLink = false;
			while((line = in.readLine()) != null){ 
				if (!line.contains("\"/" + link.replace('/', ' ').trim() + "\"")){
					conteudoArquivo.append(line);
					conteudoArquivo.append(System.getProperty("line.separator"));
					hasLink = true;
				}
			}
			in.close();
			
			if (hasLink){
				java.io.FileOutputStream fos = new java.io.FileOutputStream (file);   
				fos.write(conteudoArquivo.toString().getBytes());   
				fos.close();  
			}		

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ filePathFileName +" n�o existe.");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro na leitura de " + filePathFileName +".");
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Substitui a entrada linkOld encontrada no arquivo XML pela nova chamada de linkNew.
	 * @param linkOld
	 * @param linkNew
	 * @param filePathFileName
	 */
	/**
	 * @param linkOld
	 * @param linkNew
	 * @param filePathFileName
	 */
	public void atualizaInterceptUrlXML(String linkOld, String linkNew, String filePathFileName) {
		
		if (linkOld == null || linkOld.trim().equals("")){
			//throw new Exception("N�o foi informado o nome da p�gina antiga a ser substituida em intercept-url do xml");
			log.debug("N�o foi informado o nome da p�gina antiga a ser substituida em intercept-url do xml");
			System.out.println("N�o foi informado o nome da p�gina antiga a ser substituida em intercept-url do xml");
		}
		if (linkNew == null || linkNew.trim().equals("")){
			//throw new Exception("N�o foi informado o nome da nova p�gina a ser substituida em intercept-url do xml");
			log.debug("N�o foi informado o nome da nova p�gina a ser substituida em intercept-url do xml");
			System.out.println("N�o foi informado o nome da nova p�gina a ser substituida em intercept-url do xml");
		}
		if (filePathFileName == null || filePathFileName.trim().equals("")){
			//throw new Exception("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
			log.debug("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
			System.out.println("N�o foi informado o caminho completo com nome do arquivo xml a ser verificado");
		}
		
		try {

			File file = new File(filePathFileName);

			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String line = "";
			StringBuffer conteudoArquivo = new StringBuffer("");
			boolean hasLink = false;
			while((line = in.readLine()) != null){ 
				if (line.contains("\"/" + linkOld.replace('/', ' ').trim() + "\"")){
					conteudoArquivo.append("		<intercept-url pattern=\"/" + linkNew 
							+ "\" access=\"hasRole('"+ linkNew +"')\"/>");
					conteudoArquivo.append(System.getProperty("line.separator"));
					hasLink = true;
				}else {
				conteudoArquivo.append(line);
				conteudoArquivo.append(System.getProperty("line.separator"));
				}
			}
			in.close();
			
			if (hasLink){
				java.io.FileOutputStream fos = new java.io.FileOutputStream (file);   
				fos.write(conteudoArquivo.toString().getBytes());   
				fos.close();  
			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ filePathFileName +" n�o existe.");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro na leitura de " + filePathFileName +".");
			System.out.println(e.getMessage());
		}
	}
}
