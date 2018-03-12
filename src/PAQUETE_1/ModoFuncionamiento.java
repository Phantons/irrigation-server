package PAQUETE_1;
import java.util.ArrayList;

public class ModoFuncionamiento {
	private String nombre;
	private boolean estaActivo;
	private boolean haceCasoAlTiempo;
	private ArrayList<Electrovalvula> electrovalvulas;
	
	public ModoFuncionamiento(String nombre){
		this.nombre = nombre;
		this.estaActivo = false;
		this.haceCasoAlTiempo = false;
		this.electrovalvulas = new ArrayList<Electrovalvula>();
	}
	public String getNombre(){
		return this.nombre;
	}
	public void setNombre(String nuevoNombre){
		this.nombre = nuevoNombre;
	}
	public boolean getTiempo(){
		return this.haceCasoAlTiempo;
	}
	public void changeTiempo(){
		this.haceCasoAlTiempo = !this.haceCasoAlTiempo;
	}
	public boolean getActivo(){
		return this.estaActivo;
	}
	public void changeActivo(){
		this.estaActivo = !this.estaActivo;
	}
	public ArrayList<Electrovalvula> getElectrovalvulas(){
		return this.electrovalvulas;
	}
	public void setElectrovalvulas(ArrayList<Electrovalvula> nuevasElectrovalvulas){
		this.electrovalvulas = nuevasElectrovalvulas;
	}
}