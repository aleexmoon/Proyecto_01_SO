/**
 * Clase Lista
 */
public class Lista {
    private Nodo primero;   
    private Nodo ultimo;    
    private Nodo cursor;    
    private int length;     

    public Lista() {
        this.primero = null;
        this.ultimo = null;
        this.length = 0;
    }

    public void insertar( Proceso proceso ) {
        Nodo nuevoProceso = new Nodo( proceso );
        
        if ( this.primero == null ) {
            
            nuevoProceso.setIndex(0);
            this.primero = nuevoProceso;
            this.ultimo = nuevoProceso;
            this.length++;
            return;
        } 
        
        if ( this.primero.equals(this.ultimo) ) {
            
            this.primero.setSiguiente(nuevoProceso);
            nuevoProceso.setAnterior(this.primero);
            nuevoProceso.setIndex(1);
            this.ultimo = nuevoProceso;
            this.length++;
            return;
        }
        
        this.ultimo.setSiguiente(nuevoProceso);
        nuevoProceso.setAnterior(this.ultimo);
        nuevoProceso.setIndex(this.ultimo.getIndex() + 1);
        this.ultimo = nuevoProceso;
        this.length++;
    }

    public Proceso sacar() {
        
        if ( this.primero != null ) {
            Nodo temp = this.primero;
            
            if ( this.primero.getSiguiente() == null ) {
    
                this.primero = null;
                this.ultimo = null;
                this.length--;
    
                return temp.getProceso();
            }

            this.cursor = this.primero;
            for ( int i = 0; i < this.length-1; i++ ) {
                this.cursor.getSiguiente().setIndex(this.cursor.getIndex());;
                this.cursor = this.cursor.getSiguiente();
            }

            this.primero = this.primero.getSiguiente();
            this.primero.setAnterior( null );
            this.length--;

            return temp.getProceso();
        }

        return null;
    }

    public Proceso peak() {
        return this.primero.getProceso();
    }

    public String listar() {

        this.cursor = this.primero;
        String cadena = "";

        for ( int i = 0; i < this.length; i++ ) {
            cadena += this.cursor.getProceso().getNombre() + ", ";
            this.cursor = this.cursor.getSiguiente();
        }

        return cadena;
    }

    public Lista merge_sort(){

        this.cursor = this.primero;

        if ( this.length <= 1 )    
            return this;

        Lista izq = new Lista();
        Lista der = new Lista();

        for ( int i = 0; i < this.length; i++ ) {
            
            if ( i < (this.length)/2 )
                izq.insertar(this.cursor.getProceso());
            
            else
                der.insertar(this.cursor.getProceso());

            this.cursor = this.cursor.getSiguiente();
        }

        izq = izq.merge_sort();
        der = der.merge_sort();

        return merge( izq, der );
    }

    private Lista merge( Lista izq, Lista der ) {
        Lista resultado = new Lista();

        while ( izq.getLength() > 0 && der.getLength() > 0 ) {
            if ( izq.getPrimero().getProceso().getTiempoLlegada() <= der.getPrimero().getProceso().getTiempoLlegada() )
                resultado.insertar( izq.sacar() );
            else
                resultado.insertar( der.sacar() );
        }

        while ( izq.getLength() > 0 ) {
            resultado.insertar( izq.sacar() );
        }

        while ( der.getLength() > 0 ) {
            resultado.insertar( der.sacar() );
        }

        return resultado;
    }


    public int getLength() {
        return length;
    }

    public Nodo getPrimero() {
        return primero;
    }
    
    public Nodo getUltimo(){
        return ultimo;
    }


    public Nodo getCursor() {
        return cursor;
    }

    public void setCursor(Nodo cursor) {
        this.cursor = cursor;
    }
}