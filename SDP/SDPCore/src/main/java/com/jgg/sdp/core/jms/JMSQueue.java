/**
 * Interfaz que deben implementar todos los sistemas de mensajeria 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.jms;

public interface JMSQueue {

	/**
     * Abre una cola para lectura o consumer
     *
     * @param qName Nombre de la cola
     */
	void   openInput(String qName);
	
	/**
     * Open ua cola en modo escritura o producer
     *
     * @param qName Nombre de la cola
     */
	void   openOutput(String qName);
	
	/**
     * Cierra la cola
     */
	void   close();
	
	/**
     * Escribe el mensaje en la cola
     *
     * @param txt Mensaje a escribir
     */
	void   put(String txt);
	
	/**
     * Lee un mensaje de la cola
     *
     * @return El mensaje leido
     */
	String get();
	
}
