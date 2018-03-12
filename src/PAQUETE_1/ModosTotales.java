package PAQUETE_1;
import java.util.ArrayList;

public class ModosTotales {
	private static ArrayList<ModoFuncionamiento> modosExistentes = new ArrayList<ModoFuncionamiento>();
	
	public static ArrayList<ModoFuncionamiento> getModos(){
		return ModosTotales.modosExistentes;
	}
}