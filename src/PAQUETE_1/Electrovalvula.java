package PAQUETE_1;
import java.util.ArrayList;

public class Electrovalvula {
	private String nombre;
	private int numeroPuerto;
	private ArrayList<Pareja> tiemposRiego;
	
	public Electrovalvula(String nombre, int numeroPuerto){
		this.nombre = nombre;
		this.numeroPuerto = numeroPuerto;
		this.tiemposRiego = new ArrayList<Pareja>();
	}
	public String getNombre(){
		return this.nombre;
	}
	public void setNombre(String nuevoNombre){
		this.nombre = nuevoNombre;
	}
	public int getNumeroPuerto(){
		return this.numeroPuerto;
	}
	public void setNumeroPuerto(int nuevoPuerto){
		this.numeroPuerto = nuevoPuerto;
	}
	public ArrayList<Pareja> getTiemposRiego(){
		return this.tiemposRiego;
	}
	public void setTiemposRiego(ArrayList<Pareja> nuevosTiempos){
		this.tiemposRiego = nuevosTiempos;
	}
}