package cobra.controleacesso.web.util;

import cobra.controleacesso.web.file.FileHandler;

/**
 * Apenas pra testar a inclusão de nova entra do tipo intercep-url no XML applicationContext.xml
 * @author Antonio Augusto
 *
 */
public class FileHandlerTest {

	private static final String FILE_CRIA = "CRIA";
	private static final String FILE_ATUALIZA = "ATUALIZA";
	private static final String FILE_REMOVE = "REMOVE";
	
	/**
	 *  Para atualizar essa classe pode chamar via linha de comando passando os seguinte arquimentos:
	 * 1 - Caminho completo com o nome do arquivo xml;
	 * 2 - Nome da entrada a ser interceptada no XML em "intercept-url";
	 * 3 - Tipo da operação que pode ser cria, atualiza ou remove. Não é case sensitive;
	 * 4 - No caso de ser atualização, passar o novo nome que irá substituir o antigo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileHandler fileHandler = new FileHandler();
		String filePathFileName = "";
		String linkPaginaIntercet = "";
		String tipoOperacao = "";
		String linkPaginaIntercetNovo = "";
		
		if (args.length == 0){
			filePathFileName = System.getProperty("user.dir")
				+ System.getProperty("file.separator") +"WebContent"
				+ System.getProperty("file.separator") + "WEB-INF"
				+ System.getProperty("file.separator") + "applicationContext.xml";
			
			linkPaginaIntercet = "tetete.jsf";
			linkPaginaIntercetNovo = "teteteAindaMaisNovo.jsf";
			
			fileHandler.criaInterceptUrlXML(linkPaginaIntercet, filePathFileName);
			//fileHandler.removeIntercelUrlXML(linkPaginaIntercet, filePathFileName);
			//fileHandler.atualizaIntercelUrlXML(linkPaginaIntercet, linkPaginaIntercetNovo, filePathFileName);
			
		}else {
			filePathFileName = args[0].toString();
			
			if (args[1].toString() != null)
				linkPaginaIntercet = args[1].toString().trim();
			
			if (args[2].toString() != null)
				tipoOperacao = args[2].toString().trim().toUpperCase();
			
			if (args[3].toString() != null)
				linkPaginaIntercetNovo = args[3].toString().trim().toUpperCase();
		}
		
		switch (tipoOperacao){
			case FILE_CRIA:
				fileHandler.criaInterceptUrlXML(linkPaginaIntercet, filePathFileName);
			case FILE_ATUALIZA:
				fileHandler.atualizaInterceptUrlXML(linkPaginaIntercet, linkPaginaIntercetNovo, filePathFileName);
			case FILE_REMOVE:
				fileHandler.removeInterceptUrlXML(linkPaginaIntercet, filePathFileName);
		}
	}
}
