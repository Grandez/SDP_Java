/**
 * Implementa las funciones estaticas de manejo de fechas y timestamps 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.tools;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.*;

public class Fechas {

	// Rangos de fechas
	public static final int RANGE_ALL     = 0;
	public static final int RANGE_DAY     = 1;
	public static final int RANGE_WEEK    = 2;
	public static final int RANGE_MONTH   = 3;
	public static final int RANGE_QUARTER = 4;
	
	private static Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 *  Genera un numero unico como:
	 *  currentTimeMillis - el valor correspondiente a 2015 + dos digitos entre 0 y 99
	 * @return un numero como numero de serie unico
	 */
	
	public static long serial() {
		return ((System.currentTimeMillis() - 1419120000000L) * 1000) +  rnd.nextInt(1000);
	}
	
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
    /**
     * Obtiene un timestamp anterior a la fecha actual
     * en funcion del rango indicado
     *
     * @param rango Indicador del rango
     * @return El Timestamp calculado
     */
    public static Timestamp calculaInicio (Integer rango) {
        switch (rango) {
           case Fechas.RANGE_DAY:     return Fechas.subDays(1);
           case Fechas.RANGE_WEEK:    return Fechas.subDays(7);
           case Fechas.RANGE_MONTH:   return Fechas.subMonths(1);
           case Fechas.RANGE_QUARTER: return Fechas.subMonths(3);
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
