package PAQUETE_1;

public class Controlador {
	private String idRaspberry;
	private String objetoSerializado;
	private Boolean[] estadoRiego;
	private Integer[] regarAhora;
	
	public Controlador(String idControlador, String objetoSerializado){
		this.idRaspberry = idControlador;
		this.objetoSerializado = objetoSerializado;
		this.estadoRiego = new Boolean[32];
		this.regarAhora = new Integer[32];
		for(int j1 = 0; j1 < 32; j1 = j1 + 1){
			estadoRiego[j1] = null;
			regarAhora[j1] = 0;
		}
	}
	public synchronized String getIdRaspberry(){
		return this.idRaspberry;
	}
	public synchronized String getObjetoSerializado(){
		return this.objetoSerializado;
	}
	public synchronized void setObjetoSerializado(String nuevoObjeto){
		this.objetoSerializado = nuevoObjeto;
	}
	public synchronized Boolean[] getEstadoRiego(){
		return this.estadoRiego;
	}
	public synchronized void setEstado(Boolean[] nuevoEstado){
		this.estadoRiego = nuevoEstado;
	}
	public synchronized Integer[] getRegarAhora(){
		return this.regarAhora;
	}
	public synchronized boolean regarConcreto(int valvula, int tiempo){
		if(estadoRiego[valvula] != null){
			regarAhora[valvula] = tiempo;
			return true;
		}
		else{
			return false;
		}
	}
}