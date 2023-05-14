/**
 * Clase Proceso
 * @author jimen
 */
public class Proceso {
    private int id;                 
    private String nombre;          
    private int tamano;             
    private int _rafaga;            
    private int _tiempoLlegada;     
    private int rafaga;             
    private int tiempoLlegada;      
    private int tiempoTerminado;    
    private int tiempoEspera;       
    private int tiempocarga;
    private int tiemposalida;
    private int tejecutado;
    private int primeravez;

    public Proceso( int id, String nombre, int tamano, int rafaga, int tiempoLlegada ) {
        this.id = id;
        this.nombre = nombre;
        this.tamano = tamano;
        this.rafaga = rafaga;
        this.tiempoLlegada = tiempoLlegada;
        this._rafaga = rafaga;
        this._tiempoLlegada = tiempoLlegada;
        this.tiempoEspera = 0;
        this.tiempoTerminado = 0;
        this.tiempocarga=0;
        this.tiemposalida=0;
        this.tejecutado=0;
        this.primeravez=0;
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRafaga() {
        return rafaga;
    }

    public int get_rafaga() {
        return _rafaga;
    }

    public int getTamano() {
        return tamano;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int get_tiempoLlegada() {
        return _tiempoLlegada;
    }

    public int getTiempoTerminado() {
        return tiempoTerminado;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public int getTiempoCarga(){
        return tiempocarga;
    }
    
    public int getTiempoSalida(){
        return tiemposalida;
    }
    
    public int getTiempoEjecutado(){
        return tejecutado;
    }
    
    public int getPrimeraVez(){
        return primeravez;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void set_rafaga(int _rafaga) {
        this._rafaga = _rafaga;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public void set_tiempoLlegada(int _tiempoLlegada) {
        this._tiempoLlegada = _tiempoLlegada;
    }

    public void setTiempoTerminado(int tiempoTerminado) {
        this.tiempoTerminado = tiempoTerminado;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
    
    public void setTiempoCarga(int tiempocarga){
        this.tiempocarga=tiempocarga;
    }
    public void setTiempoSalida(int tiemposalida){
        this.tiemposalida=tiemposalida;
    }
    
    public void setTiempoEjecutado(int tejecutado){
        this.tejecutado=this.tejecutado+tejecutado;
    }
    
    public void setPrimeraVez(int primeravez){
        this.primeravez=primeravez;
    }
}
