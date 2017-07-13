/**
 * Implementa un comentario
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

public class Comment {

	private int lines = 0;         // Lineas de comentarios
	private int decorators = 0;    // Lineas de decoracion (asteriscos, cosas)
	private int docs = 0;          // Lineas de documentacion
	private int bloques = 0;       // Numero de bloques de comentarios
	
	
	public void incLines()      { lines++;       }
	public void incDocs()       { docs++;        }
	public void incDecorators() { decorators++;  }
	public void incBlocks()     { bloques++;     }
	
	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getDecorators() {
		return decorators;
	}

	public void setDecorators(int decorators) {
		this.decorators = decorators;
	}

	public int getBloques() {
		return bloques;
	}

	public void setBloques(int bloques) {
		this.bloques = bloques;
	}
	public int getDocs() {
		return docs;
	}
	public void setDocs(int docs) {
		this.docs = docs;
	}
	

}
