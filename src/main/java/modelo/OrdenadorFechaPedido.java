package modelo;

import java.util.Comparator;
import modelo.CabeceraPedido;

public class OrdenadorFechaPedido implements Comparator<CabeceraPedido>{    
    //Permite ordenar de mas reciente a mas antigua los reportes de los juegos de los estudiantes
     @Override
     public int compare(CabeceraPedido o1, CabeceraPedido o2) {
         return o2.getFecha().compareTo(o1.getFecha());
     }      
}
