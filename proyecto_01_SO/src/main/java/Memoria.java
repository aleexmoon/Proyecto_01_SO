/**
 * Clase Memoria
 * @author jimen
 */
public class Memoria {
    private Lista colaDeProcesosListos;     
    private long tamano;                    

    public Memoria( long tamano ) {
        this.colaDeProcesosListos = new Lista();
        this.tamano = tamano;
    }

    public Lista getColaDeProcesosListos() {
        return this.colaDeProcesosListos;
    }


    public long getTamano() {
        return this.tamano;
    }


    public boolean cargar( Proceso proceso ) {
        
        if ( this.tamano > proceso.getTamano() ) {          
            this.colaDeProcesosListos.insertar(proceso);    
            this.tamano -= proceso.getTamano();             
            
            return true;                                    
        }
        return false;                                       
    }


    public Proceso sacar() {
 
        if ( this.colaDeProcesosListos.getLength() > 0 ) {          
            Proceso proceso = this.colaDeProcesosListos.sacar();    
            this.tamano += proceso.getTamano();                     

            return proceso;                                         
        }
        return null;                                                
    }
}
