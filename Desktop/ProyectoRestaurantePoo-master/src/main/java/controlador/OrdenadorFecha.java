package controlador;

import java.util.Comparator;
import modelo.ReporteVentas;

public class OrdenadorFecha implements Comparator<ReporteVentas>{    
    //Permite ordenar de mas reciente a mas antigua los reportes de los juegos de los estudiantes
     @Override
     public int compare(ReporteVentas o1, ReporteVentas o2) {
         return o2.getFechaPedido().compareTo(o1.getFechaPedido());
     }      
}

