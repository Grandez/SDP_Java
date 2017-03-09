/**
 * Implementa las funciones estaticas de manejo de fechas y timestamps 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.tools;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.*;

import com.jgg.sdp.core.ctes.CDG;

public class Fechas {

    /**
     * Obtiene un timestamp anterior a la fecha actual
     * en funcion del rango indicado
     *
     * @param rango Indicador del rango
     * @return El Timestamp calculado
     */
    public static Timestamp calculaInicio (Integer rango) {
        switch (rango) {
           case CDG.RANGE_DAY:     return Fechas.subDays(1);
           case CDG.RANGE_WEEK:    return Fechas.subDays(7);
           case CDG.RANGE_MONTH:   return Fechas.subMonths(1);
           case CDG.RANGE_QUARTER: return Fechas.subMonths(3);
        }
        return new Timestamp(0L);
    }

    /**
     * Incrementa/Decrementa un numero de dias a un Timestamp
     *
     * @param inc      El incremento a aplicar 
     * @param current  El timestamp 
     * @return Un nuevo timestamp con la variacion en dias
     */
    public static Timestamp changeDay(int inc, Timestamp current) {
        return add(inc, current, ChronoUnit.DAYS);
    }
    
    /**
     * Incrementa/Decrementa un numero de semanas a un Timestamp
     *
     * @param inc      El incremento a aplicar 
     * @param current  El timestamp 
     * @return Un nuevo timestamp con la variacion en semanas
     */
    public static Timestamp changeWeek(int inc, Timestamp current) {
        return add(inc * 7, current, ChronoUnit.DAYS);
    }
    
    /**
     * Incrementa/Decrementa un numero de meses a un Timestamp
     *
     * @param inc      El incremento a aplicar 
     * @param current  El timestamp 
     * @return Un nuevo timestamp con la variacion en meses
     */
    public static Timestamp changeMonth(int inc, Timestamp current) {
        return add(inc, current, ChronoUnit.MONTHS);
    }
    
    /**
     * Incrementa/Decrementa un numero de dias a un Timestamp
     *
     * @param inc      El incremento a aplicar 
     * @param current  El timestamp 
     * @return Un nuevo timestamp con la variacion en dias
     */
    public static Timestamp subDays(int inc) {
        return subtract(inc, new Timestamp(System.currentTimeMillis()),ChronoUnit.DAYS);
    } 

    /**
     * Incrementa/Decrementa un numero de meses a un Timestamp
     *
     * @param inc      El incremento a aplicar 
     * @param current  El timestamp 
     * @return Un nuevo timestamp con la variacion en meses
     */
    public static Timestamp subMonths(int inc) {
        return subtract(inc, new Timestamp(System.currentTimeMillis()),ChronoUnit.MONTHS);
    } 
    
    @SuppressWarnings("deprecation")
    private static Timestamp add(int inc, Timestamp current, TemporalUnit units) {
        LocalDateTime dt = current.toLocalDateTime();
        dt = dt.plus(inc, units);
        return new Timestamp(dt.getYear(), dt.getMonth().getValue(), dt.getDayOfMonth(), 0, 0, 0, 0);

    }

    @SuppressWarnings("deprecation")
    private static Timestamp subtract(int inc, Timestamp current, TemporalUnit units) {
        LocalDateTime dt = current.toLocalDateTime();
        dt = dt.minus(inc, units);
        return new Timestamp(dt.getYear() - 1900, dt.getMonth().getValue() - 1, dt.getDayOfMonth(), 0, 0, 0, 0);

    }

}
