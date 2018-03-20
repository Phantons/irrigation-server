package PAQUETE_1;
import java.util.ArrayList;

public class Usuario_Concreto_General {
	private String nombreUsuario;
	private String contrasena;
	private ArrayList<Controlador> controladores;
	
	public Usuario_Concreto_General(String nombreDeUsuario, String contrasena){
		this.nombreUsuario = nombreDeUsuario;
		this.contrasena = contrasena;
		this.controladores = new ArrayList<Controlador>();
	}
	public String getNombreUsuario(){
		return this.nombreUsuario;
	}
	public synchronized String getContrasena(){
		return this.contrasena;
	}
	public synchronized void setContrasena(String nuevaContrasena){
		this.contrasena = nuevaContrasena;
	}
	public synchronized ArrayList<String> getControladoresSerializados(){
		ArrayList<String> salida = new ArrayList<String>();
		for(int j1 = 0; j1 < this.controladores.size(); j1 = j1 + 1){
			salida.add(this.controladores.get(j1).getObjetoSerializado());
		}
		return salida;
	}
	public synchronized Controlador getControladorPorId(String id){
		for(int j1 = 0; j1 < this.controladores.size(); j1 = j1 + 1){
			if(this.controladores.get(j1).getIdRaspberry().equals(id)){
				return this.controladores.get(j1);
			}
		}
		return null;
	}
	public synchronized void anadirControlador(Controlador nuevoControlador){
		this.controladores.add(nuevoControlador);
	}
}