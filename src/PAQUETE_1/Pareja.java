package PAQUETE_1;

public class Pareja {
    private int minutoInicial;
    private int minutoFinal;

    public Pareja(int inicioRiego, int finRiego){
        this.minutoInicial = inicioRiego;
        this.minutoFinal = finRiego;
    }
    public int getInicio(){
        return this.minutoInicial;
    }
    public int getFinal(){
        return this.minutoFinal;
    }
}