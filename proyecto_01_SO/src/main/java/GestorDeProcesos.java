import java.util.*;

/**
 * Gestor de Procesos
 * @author jimen
 * QUANTUM siempre sera un valor fijo por lo que sera estatico
 */
public class GestorDeProcesos {

    static int QUANTUM = 4;
    static int tiempo = 0;
    static String gant = new String();

    static Memoria memoria = new Memoria(1024);
    static Lista colaDeProcesos = new Lista();  
    static Lista ProcFin = new Lista();
    static LinkedList<String> listaProcesos = new LinkedList<String>(); 


    public static void main(String[] args) {
        System.out.println(colaDeProcesos.getLength());   
        
        inicio(); 
        captura_datos(); 
        if(colaDeProcesos.getLength()!=0){
            System.out.println("La cola de procesos inicia: " + colaDeProcesos.listar());
            int tamano = colaDeProcesos.getLength(); 
            colaDeProcesos = colaDeProcesos.merge_sort();  
            // System.out.println("Después de ordenar la lista: " + colaDeProcesos.listar()); 
            tiempo = colaDeProcesos.peak().getTiempoLlegada();  

            planificadorMedianoPlazo(); 
            planificadorCortoPlazo();   

            System.out.println("El diagrama de gant final es: " + gant);  

            promedios(tamano); 
        }else{
            System.out.println("Error. No hay procesos a ordenar");
        }
     }


    public static void captura_datos(){
        int n;
    
        Scanner quantum = new Scanner(System.in);
        System.out.print("Con que Quantum desea trabajar?\n");
        int Q = quantum.nextInt();    
        GestorDeProcesos.QUANTUM=Q;
        
        Scanner entrada = new Scanner(System.in);
        System.out.print("Cuántos procesos desea crear?\n");
        n = entrada.nextInt();  
        
        
        Proceso proceso[] = new Proceso[n];
        int id=1;
        for (int i =0;i<n;i++){
            System.out.print("Deme en nombre del proceso " + id +"\n");
            String nombre=entrada.next();
            
            System.out.print("Deme el tiempo de rafaga del proceso " + nombre +"\n");
            int trafaga=entrada.nextInt();
            
            System.out.print("Deme el tiempo de llegada del proceso " + nombre +"\n");
            int tllegada=entrada.nextInt();
            
            System.out.print("Deme el tamaño del proceso " + nombre +"\n");
            int tamano= entrada.nextInt();

            proceso[i]=new Proceso(id, nombre, tamano ,trafaga,tllegada);
            
            colaDeProcesos.insertar(proceso[i]);
            
            id++;
            System.out.print("\n\n");
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();

        entrada.close();
    }

    public static void inicio(){
        imprimir();
        Scanner teclado = new Scanner (System.in);
        String seguir;
        System.out.print(" |Integrantes:					  	| \n "
        		+ "|\tHernandez Vazquez Daniela			| \n "
        		+ "|\tLopez Carrasco Karla				| \n "
        		+ "|\tJimenez Cervantes Angel Mauricio		| \n "
        		+ "|\tRodriguez Garcia Valeria Alejandra		| \n"
        		+ " |------------------------------------------------------|\n\n\n "
        		+ "Presione una tecla para continuar...");
        try
        {
            seguir = teclado.nextLine();
        }
        catch(Exception e)
        {}
        System.out.print("\033[H\033[2J");
    }
    public static void imprimir(){
        System.out.print("|================================================================================|\n");
        System.out.print("|	Simulador Planificador de procesos  Round Robin \t Equipo 2 	 |\n");
        System.out.print("|================================================================================|\n");
    }

    public static void planificadorMedianoPlazo() {

        int tamanoDeLaCola = colaDeProcesos.getLength();    
        for ( int i = 0; i < tamanoDeLaCola ; i++ ) {  

            if ( colaDeProcesos.peak() != null && colaDeProcesos.peak().getTamano() < memoria.getTamano() ) {
                if ( colaDeProcesos.peak().getTiempoLlegada() <= tiempo ) {    
                    
                    System.out.println("Cargando en memoria: " + colaDeProcesos.peak().getNombre());
                    for (int a = 0 ; a < QUANTUM ; a++) 
                    {
                    	System.out.println("La rafaga actual del proceso " + colaDeProcesos.peak().getNombre() + " es " + (colaDeProcesos.peak().getRafaga() - a));
                    }
                    memoria.cargar(colaDeProcesos.sacar()); 
                    System.out.println("Se actualizo la memoria: " + memoria.getColaDeProcesosListos().listar());
                    //Va el for
                    System.out.println("El tamaño actual de la memoria es: " + memoria.getTamano()); 
                    
                } else { 
                    colaDeProcesos.insertar(colaDeProcesos.sacar()); 
                    System.out.println("Se actualizo la cola de procesos: " + colaDeProcesos.listar()); 
                }
            
            } else { //si el 
                System.out.println("No hay memoria suficiente para el proceso " + colaDeProcesos.peak().getNombre());
                break;
            }    
        }            
    }

    public static void planificadorCortoPlazo() {

        while (true){
            boolean bandera = true; 

            for (int i = 0; i < memoria.getColaDeProcesosListos().getLength(); i++){ 
                System.out.println("Tiempo actual: " + tiempo); 
                System.out.print("Nuestro diagrama de gant seria:"+gant+"\n\n");  

                Proceso proceso_i = memoria.getColaDeProcesosListos().peak();
                if (!listaProcesos.contains(proceso_i.getNombre())){    
                    listaProcesos.add(proceso_i.getNombre());         
                    proceso_i.setPrimeraVez(tiempo);            
                }

                System.out.println("Proceso a cargar en CPU: " + proceso_i.getNombre());
                    
                if (proceso_i.get_tiempoLlegada() <= QUANTUM){ 

                    if (proceso_i.get_rafaga() > 0){  

                        bandera = false;   

                        if (proceso_i.getRafaga() > QUANTUM){  

                            proceso_i = memoria.sacar();
                            System.out.println("Se actualizo la memoria: " + memoria.getColaDeProcesosListos().listar());

                            tiempo += QUANTUM;  
                            proceso_i.set_rafaga(proceso_i.get_rafaga() - QUANTUM); 

                            proceso_i.set_tiempoLlegada(proceso_i.get_tiempoLlegada() + QUANTUM);

                            colaDeProcesos.insertar(proceso_i);
                            System.out.println("La cola de procesos es: " + colaDeProcesos.listar()); 
                            planificadorMedianoPlazo();

                            proceso_i.setTiempoEjecutado(QUANTUM);

                            gant += ","+proceso_i.getNombre();

                        } else {  

                            proceso_i.setTiempoCarga(tiempo); 

                            proceso_i = memoria.sacar();
                            System.out.println("Se actualizo la memoria: " + memoria.getColaDeProcesosListos().listar());

                            tiempo += proceso_i.get_rafaga();

                     
                            
                            proceso_i.setTiempoSalida(tiempo);

                            System.out.print("\nTiempos "+proceso_i.getNombre()+":");
                            System.out.print("\n\tTiempo ejec: "+proceso_i.getTiempoEjecutado());
                            System.out.print("\t\tTiempo de carga: "+proceso_i.getTiempoCarga());
                            System.out.println("\nTiempo salida proceso: "+ proceso_i.getTiempoSalida()+ "\n");
                            
                            ProcFin.insertar(proceso_i);

                            proceso_i.setTiempoTerminado(tiempo - proceso_i.getTiempoLlegada());
                            proceso_i.setTiempoEspera(tiempo - proceso_i.getRafaga() - proceso_i.getTiempoLlegada());
                            proceso_i.set_rafaga(0);

                            planificadorMedianoPlazo();
                            gant +=","+proceso_i.getNombre();
                        }
                    }
                } else {

                    if (proceso_i.get_rafaga() > 0){
                        
                        bandera = false;

                        if (proceso_i.get_rafaga() > QUANTUM){

                            proceso_i = memoria.sacar();
                            System.out.println("Se actualizo la memoria 2: " + memoria.getColaDeProcesosListos().listar());

                            tiempo += QUANTUM;
                            proceso_i.set_rafaga(proceso_i.get_rafaga() - QUANTUM); 
                            proceso_i.set_tiempoLlegada(proceso_i.get_tiempoLlegada() + QUANTUM);

                            colaDeProcesos.insertar(proceso_i);
                            System.out.println("La cola de procesos es: " + colaDeProcesos.listar()); 
                            planificadorMedianoPlazo();

                            proceso_i.setTiempoEjecutado(QUANTUM);
                            gant += ","+proceso_i.getNombre();

                        } else {
                            proceso_i.setTiempoCarga(tiempo);
                            proceso_i = memoria.sacar();
                            System.out.println("Se actualizo la memoria: " + memoria.getColaDeProcesosListos().listar());

                            tiempo += proceso_i.get_rafaga();

                            proceso_i.setTiempoSalida(tiempo);
                            System.out.print("\nTiempos "+proceso_i.getNombre()+":");
                            System.out.print("\n\tTiempo ejec: "+proceso_i.getTiempoEjecutado());
                            System.out.print("\t\tTiempo de carga: "+proceso_i.getTiempoCarga());
                            System.out.println("\nTiempo salida proceso: "+ proceso_i.getTiempoSalida()+ "\n");
                           
                            ProcFin.insertar(proceso_i);

                            proceso_i.setTiempoTerminado(tiempo - proceso_i.getTiempoLlegada());
                            proceso_i.setTiempoEspera(tiempo - proceso_i.getRafaga() - proceso_i.getTiempoLlegada());
                            proceso_i.set_rafaga(0);

                            planificadorMedianoPlazo();
                            gant +=","+proceso_i.getNombre();
                        }
                    }
                }
            }
            
            if(bandera){
                break;
            }
        
        }
    }
    
    public static void promedios(int tamano){

        float tresp = 0,
              tesp = 0,
              tejec = 0,
              carga,
              ejec,
              salida,
              tllegada,
              tprimera;

        System.out.println("La cola con los tiempos finales es: "+ProcFin.listar());

        ProcFin = ProcFin.merge_sort();
        ProcFin.setCursor( ProcFin.getPrimero() );
        
        for(int i = 0; i < ProcFin.getLength(); i++){

            carga = ProcFin.getCursor().getProceso().getTiempoCarga();
            ejec = ProcFin.getCursor().getProceso().getTiempoEjecutado();
            salida = ProcFin.getCursor().getProceso().getTiempoSalida();
            tllegada = ProcFin.getCursor().getProceso().getTiempoLlegada();
            tprimera= ProcFin.getCursor().getProceso().getPrimeraVez();
            
            System.out.print("\nProceso: " + ProcFin.getCursor().getProceso().getNombre());
            System.out.print("\n \t Tiempo de Carga: "+ tprimera + "\n \t Tiempo de ejec: "+ ejec +"\n \t Tiempo de salida: "+salida+"\n \t Tiempo de llegada: "+tllegada);
            
            tesp += (carga - tllegada - ejec);
            tejec += (salida-tllegada);
            tresp += (tprimera-tllegada);
            
            

            ProcFin.setCursor(ProcFin.getCursor().getSiguiente());    
        }

        tresp = (tresp / ProcFin.getLength());
        tejec = (tejec/ProcFin.getLength());
        tesp = (tesp/ProcFin.getLength());
        
        System.out.print("\n El tiempo promedio de espera es: " + tesp + " mseg"
                         + "\n El tiempo de ejecución promedio es: "+tejec+" mseg"
                         + "\n El tiempo respuesta es: "+tresp+" mseg\n");

    }
}
