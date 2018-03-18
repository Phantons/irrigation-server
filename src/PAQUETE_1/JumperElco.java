package PAQUETE_1;
import java.util.Scanner;

public class JumperElco {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("Jumper iniciado. Lanzando servidor...");
		System.out.println("");
		Thread_Recepcion_Controlador thread_Recepcion_Controlador = new Thread_Recepcion_Controlador();
		Thread_Recepcion_App thread_Recepcion_App = new Thread_Recepcion_App();
		thread_Recepcion_Controlador.start();
		thread_Recepcion_App.start();
		System.out.println("Servidor iniciado correctamente");
		System.out.println("");
		sc.close();
	}
}