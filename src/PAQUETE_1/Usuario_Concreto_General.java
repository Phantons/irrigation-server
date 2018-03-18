package PAQUETE_1;
import java.util.ArrayList;

public class Usuario_Concreto_General {
	private String nombreUsuario;
	private String contrasena;
	private String idRaspberry;
	private String modoActivo;
	private ArrayList<String> modosInactivos;
	private Boolean[] estadoElectrovalvulas;
	private Integer[] inicioRiegoManual;
	
	public Usuario_Concreto_General(String nombreDeUsuario, String contrasena, String idRaspi){
		this.nombreUsuario = nombreDeUsuario;
		this.contrasena = contrasena;
		this.idRaspberry = idRaspi;
		this.modoActivo = null;
		this.modosInactivos = new ArrayList<String>();
		this.estadoElectrovalvulas = new Boolean[32];
		this.inicioRiegoManual = new Integer[32];
		for(int j1 = 0; j1 < 32; j1 = j1 + 1){
			this.estadoElectrovalvulas[j1] = null;
			this.inicioRiegoManual[j1] = null;
		}
	}
	public String getNombreUsuario(){
		return this.nombreUsuario;
	}
	public String getIdRaspberry(){
		return this.idRaspberry;
	}
	public synchronized String getContrasena(){
		return this.contrasena;
	}
	public synchronized void setContrasena(String nuevaContrasena){
		this.contrasena = nuevaContrasena;
	}
	public synchronized String getModoActivo(){
		return this.modoActivo;
	}
	public synchronized void setModoActivo(String nuevoModo){
		this.modoActivo = nuevoModo;
	}
	public synchronized ArrayList<String> getModosInactivos(){
		return this.modosInactivos;
	}
	public synchronized void setModosInactivos(ArrayList<String> nuevosModos){
		this.modosInactivos = nuevosModos;
	}
	public synchronized Boolean[] getEstadoElectrovalvulas(){
		return this.estadoElectrovalvulas;
	}
	public synchronized void setEstadoElectrovalvulas(Boolean[] nuevoEstado){
		this.estadoElectrovalvulas = nuevoEstado;
	}
	public synchronized Integer[] getInicioManual(){
		return this.inicioRiegoManual;
	}
	public synchronized void setInicioManual(Integer[] data){
		this.inicioRiegoManual = data;
	}
}