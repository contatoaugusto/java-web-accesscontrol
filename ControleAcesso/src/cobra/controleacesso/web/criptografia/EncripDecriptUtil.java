package cobra.controleacesso.web.criptografia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Antonio Augusto
 *
 */
public class EncripDecriptUtil {
	
	/**
	 * A partir de um string gera um criptografia com algorítmo tipo definido em "algoritmo" (ex. MD5 de 16 bits, SHA1, etc).
	 * @param senha
	 * @return String
	 */
	public String encripta(String senha, String algoritmo) {

		MessageDigest md;
		String senhaCriptografada = "";
		try {

			md = MessageDigest.getInstance(algoritmo);
			md.update(senha.getBytes());
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			senhaCriptografada = bigInt.toString(16);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return senhaCriptografada;
	}

	public static void main(String[] args) {
		String senha = new EncripDecriptUtil().encripta("teste", "SHA1");
	}
}
