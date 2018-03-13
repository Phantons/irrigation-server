package PAQUETE_1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Thread_TCP_Cifrado extends Thread{
	protected Socket socket;
	private Cipher aes;
	private Key claveSimetrica;
	private DataInputStream in;
	private DataOutputStream out;
	
	public Thread_TCP_Cifrado(Socket socket){
		this.socket = socket;
	}
	protected boolean inicializacionCifrado(){
		try{
			this.socket.setSoTimeout(5000);
			this.in = new DataInputStream(this.socket.getInputStream());
			this.out = new DataOutputStream(this.socket.getOutputStream());
			int longitudClavePublica = in.readInt();
			byte[] bytesClavePublica = new byte[longitudClavePublica];
			in.readFully(bytesClavePublica);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesClavePublica);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		    keyGenerator.init(128);
		    this.claveSimetrica = keyGenerator.generateKey();
		    this.aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		    Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		    rsa.init(Cipher.ENCRYPT_MODE, publicKey);
		    byte[] claveSimetricaEncriptada = rsa.doFinal(this.claveSimetrica.getEncoded());
		    out.writeInt(claveSimetricaEncriptada.length);
		    out.write(claveSimetricaEncriptada);
		    return true;
		}catch(Exception e){
			return false;
		}
	}
	private byte[] cifrarTexto(String textoCifrar) throws Exception{
		this.aes.init(Cipher.ENCRYPT_MODE, this.claveSimetrica);
		return aes.doFinal(textoCifrar.getBytes());
	}
	private String descifrarTexto(byte[] bytesCifrados) throws Exception{
		this.aes.init(Cipher.DECRYPT_MODE, this.claveSimetrica);
		byte[] bytesDescifrados = aes.doFinal(bytesCifrados);
		return new String(bytesDescifrados);
	}
	protected void enviarTexto(String textoEnviar) throws Exception{
		byte[] textoEncriptado = this.cifrarTexto(textoEnviar);
		this.out.writeInt(textoEncriptado.length);
		this.out.write(textoEncriptado);
	}
	protected String leerTexto() throws Exception{
		int longitudRecepcion = this.in.readInt();
		byte[] bytesRecibidos = new byte[longitudRecepcion];
		this.in.readFully(bytesRecibidos);
		return this.descifrarTexto(bytesRecibidos);
	}
}