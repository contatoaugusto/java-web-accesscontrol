/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleacesso.web.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que contém os métodos para manipulação
 * de arquivo properties
 * 
 * @author Daniel Paulo de Assis
 */
public class ManipulaProperties {

    /**
     * Método que faz a leitura do arquivo properties 
     * dentro do projeto
     * 
     * @throws FileNotFoundException 
     */
    public String readPropertiesInterno(String nome_persistence_unit) throws FileNotFoundException {

        try {

            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Lendo o arquivo dentro do projeto**/
            InputStream configuracao = getClass().getResourceAsStream("/controleacesso.properties");

            /**carregando o arquivo**/
            config.load(configuracao);

            /**Imprimindo o conteúdo do arquivo**/
            return config.getProperty(nome_persistence_unit);
            
        } catch (IOException ex) {
            Logger.getLogger(ManipulaProperties.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Método que faz a leitura do arquivo properties
     * externo ao projeto
     * 
     * @throws FileNotFoundException 
     */
    public void readPropertiesExterno() throws FileNotFoundException {

        try {
            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();
            
            /**Lendo o arquivo externamente**/
            InputStream configuracao = new FileInputStream("C://controleacesso.properties");

            /**carregando o arquivo**/
            config.load(configuracao);

            System.out.println("Usuário :" + config.getProperty("user"));
            System.out.println("Senha :" + config.getProperty("senha"));


        } catch (IOException ex) {
            Logger.getLogger(ManipulaProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que edita o arquivo properties 
     * dentro do projeto (Não funcionará após gerado o jar)
     * 
     * @param usuario
     * @param senha 
     */
    public void writePropertiesInterno(String usuario, String senha) {
        
        try {

             /**Lendo o arquivo dentro do projeto**/
            URI arquivo = getClass().getResource("/controleacesso.properties").toURI();


            /**Instanciando objeto do tipo File**/
            File file = new File(arquivo);


            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Instanciando objeto do tipo FileInpusStream **/
            FileInputStream fis = new FileInputStream(file);

            /**carregando o arquivo**/
            config.load(fis);

            /**setando as propriedades no arquivo properties**/
            config.setProperty("user", usuario);
            config.setProperty("senha", senha);

            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuração Usuário e Senha:");

            fos.close();



        } catch (IOException ex) {
            ex.getMessage();
        } catch (URISyntaxException ex) {
            ex.getMessage();
        }


    }

    /**
     * Método que edita o arquivo properties 
     * externo ao projeto
     * 
     * @param usuario
     * @param senha 
     */
    public void writePropertiesExterno(String usuario, String senha) {

        try {

            /**Lendo o arquivo externamente**/
            String arquivo = "C://controleacesso.properties";

            /**Instanciando objeto do tipo File**/
            File file = new File(arquivo);


            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Instanciando objeto do tipo FileInpusStream **/
            FileInputStream fis = new FileInputStream(file);

            /**carregando o arquivo**/
            config.load(fis);

            /**setando as propriedades no arquivo properties**/
            config.setProperty("user", usuario);
            config.setProperty("senha", senha);

            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuração Usuário e Senha:");

            fos.close();



        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
}
