package PAQUETE_1;

public class Usuario_Concreto_Activo {
	private String nombreUsuario;
	private String token;
	
	public Usuario_Concreto_Activo(String user, String token){
		this.nombreUsuario = user;
		this.token = token;
	}
	public String getNombreUsuario(){
		return this.nombreUsuario;
	}
	public String getToken(){
		return this.token;
	}
}