/**
 * Almacena el codigo fuente original
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.unit;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.common.exceptions.FileException;
import com.jgg.sdp.common.files.Archive;
import com.jgg.sdp.tools.*;

import static com.jgg.sdp.common.ctes.CDG.*;

public class Source extends Reader {

	private Archive   archivo    = null;
	
	private String   firma   = null;
	private char[]   rawData = null;
	private char[]   data    = null;
	private int      size    = 0;
	private int      offset  = 0;
	private int      length  = 0;

	private int      tipo    = SOURCE_CODE;
	private long     idFile;
	private long     idFileVersion;
	
	private Configuration cfg = ConfigurationBase.getInstance();

	public Source(String fullName) {
		archivo = new Archive(fullName);
	}

	public Source(Archive archivo) {
		idFile = Fechas.serial();
		this.archivo = archivo;
		loadData(archivo, null);
		idFileVersion = Fechas.serial();
	}

	public Source (Archive archivo, ArrayList<String> toks) {
		idFile = Fechas.serial();
		this.archivo = archivo;
		loadData(archivo, toks);
		idFileVersion = Fechas.serial();
	}
	
	public Source (Archive archivo, boolean memory) {
		idFile = Fechas.serial();
		this.archivo = archivo;
		idFile = Fechas.serial();
	}
		
	private void loadData(Archive archivo, ArrayList<String> toks) {
		
        checkFile();
        
		size = ((Long)archivo.length()).intValue();
        rawData = new char[size + 1];
        
		try {
        	FileReader reader = new FileReader(archivo);
        	reader.read(rawData, 0, size);
        	reader.close();
        }
        catch (Exception e) {
	    	throw new FileException(MSG.FILE_NOT_READ, archivo.getAbsolutePath());
        }
		
		prepareData(toks);
	}
    

	public long getIdFile()               { return idFile;       }
	public void setIdFile(long id)        { this.idFile = id;    }
	public long getIdFileVersion()        { return idFileVersion;    }
	public void setIdFileVersion(long id) { this.idFileVersion = id; }
	
	public Source prepareData(ArrayList<String> toks) {
		prepararDatos();
        cleanNulls();
		
		// El reemplazo se debe hacer despues para mantener las columnas
        if (toks != null) {
        	changeTokens(toks);
        }

        offset = 0;
        return this;
	}

	public char[] getRawData() {
		return rawData;
	}

	public void setRawData(String fileName, String encoded, byte[] bytes) {
		loadRawData(fileName, encoded, bytes);
		prepararDatos();
	}

	public void setRawData(byte[] bytes, ArrayList<String> toks) {
		String str = new String(bytes, StandardCharsets.UTF_8);
		this.rawData = str.toCharArray();
		prepareData(toks);
	}
	
	public void setData(String newData) {
		this.size   = newData.length();
		this.length = newData.length();
		rawData     = newData.toCharArray();
		data        = newData.toCharArray();
		offset      = 0;
	}
	
	public void loadRawData(String fileName, String encoded, byte[] bytes) {
		if (encoded.compareTo("UTF_8") == 0) { 
		    String str = new String(bytes, StandardCharsets.UTF_8);
		    this.rawData = str.toCharArray();
		}
		
		if (encoded.compareTo("ZIP") == 0) {
			this.rawData = Zipper.unzip(fileName, bytes);
		}		
	}
	
	public char[] getData() {
		return data;
	}

    public String getBaseName() {
		return archivo.getBaseName();
	}
	
    public String getModuleName() {
		return archivo.getName();
	}
	
	public String getFullName() {
		return archivo.getAbsolutePath();
	}
	
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}

	public int getSize() {
		return size;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	public Archive getArchivo() {
		return archivo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Devuelve la palabra indicada si existe
	 * Una palabra es una secuencia de caracteres que empieza por letra o numero
	 * @param order Numero de palabra empezando por 1
	 * @return La palabra o null si no existe
	 */
	public String getWord(int order) {
		StringBuilder sb = new StringBuilder();
		int offset = 0;
		int begin = 0;
		int end   = 0;
		int count = 0;
		
		while (count < order) {
			begin = getBeginWord(offset);
			if (begin == -1) return null;
		    end = getEndWord(begin);
		    count++;
		}
		for (int i = begin; i < end; i++) sb.append(data[i]);
		return sb.toString();
	}
	
	private int getBeginWord(int offset) {
		while (offset < data.length && !Character.isLetterOrDigit(data[offset])) {
			offset++;
		}
		return (offset == data.length) ? -1 : offset;
	}
	private int getEndWord(int offset) {
		while (offset < data.length && !Character.isWhitespace(data[offset])) {
			offset++;
		}
		return offset;
	}
	
	/*
     * Quita los caracteres no relevantes por la derecha
     * y por la izquierda
     */
	private void prepararDatos() {
    	int pos = 0;
		int col = 0;

		int left  = cfg.getInteger(CFG.MARGIN_LEFT,  0) - 1;
    	int right = cfg.getInteger(CFG.MARGIN_RIGHT, 0);
		
    	if (right == 0) right = Integer.MAX_VALUE;
    	if (left   < 0) left = 0;
    	
    	firma = Firma.calculate(new String(rawData).getBytes());

    	size = rawData.length;
    	data   = new char[size+ 1];
    	length = 0;
		
    	if (right == 0) right = size;
        
    	// Lee caracter a caracter
     	while (pos < size) {
     		
             // No hay problema con size por que sera 0x0D0x0A
     		if (rawData[pos] == 0x0D) pos++;
     		
     		// Quitar el lado izquierdo
     		for (col = 0; col < left; col++) {
     			if (pos == size)  break; // EOF
     			if (rawData[pos] == 0x0A) break; // Fin de linea
     			pos++;
     		}
     		  
     		/* JGG EL FTP cambia \r por \r\n automaticamente */
     		/* JGG Esto jode la eliminacion de columnas cuando hay caracteres
     		 * no imprimibles en los comentarios
     		 * El caso \n' no deberia darse por el encolumnado
     		 */
     		// Guardar los datos
     		while (pos < size && col < right) {
     			if (rawData[pos] == 0x0A) break;
     			/*
     			if (rawData[pos] == 0x0A) {
     				if ((pos + 1) < size) {
     					if (rawData[pos + 1] == '\'' || rawData[pos + 1] == '\"') {
     						data[length++] = 'X';
     						pos++;
     	                }
     					else {
     						break;
     					}
     				}
     				else {
     				   break;
     				}	   
     			}*/
                data[length++] = rawData[pos++];
     	        col++;
     		}
     		
			// Salta los caracteres hasta fin de linea
     	    while (pos < size && rawData[pos] != 0x0A) pos++;
			
			data[length++] = 0x0A;
			pos++;			
		}
    }
    
	@Override
	public int read() throws IOException {
		return (offset == length) ? -1 : data[offset++];
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int leido = 0;

		if (offset == length) return -1;
		
		while (leido < len && offset < length) {
			cbuf[off++] = data[offset++];
			leido++;
		}
		return leido;
	}
	
	public void rewind() {
		offset = 0;
	}

	public void forward(int offset) {
        this.offset = offset;
    }
	
	@Override
	public void close() throws IOException {
       offset = length;
	}

	private void checkFile() {
		if (!archivo.hasDir() && archivo.exists() == false) {
			archivo = new Archive(cfg.getInputDir() + archivo.getFileName());
		}

	    if (archivo.exists() == false) {
	    	throw new FileException(MSG.FILE_NOT_EXIST, archivo.getAbsoluteFile());
	    }
        if (!archivo.canRead()) {
	    	throw new FileException(MSG.FILE_NOT_READ, archivo.getAbsoluteFile());
	    }
	}

	private void changeTokens(ArrayList<String> toks) {
		String from = null;
		String to   = null;
		String tmp = new String(data);
		
		for (int idx = 0; idx < toks.size(); idx++) {
			from = Pattern.quote(toks.get(idx++));
			to   = toks.get(idx).toString() ;
			tmp = tmp.replaceAll(from, to);
		}
		length = tmp.length();
	    data = new char[length + 1];
	    tmp.getChars(0, length, data, 0);

	    cleanNulls();
	}
	
    /* Segun los cambios se pueden mantener 0x00 al final y no los quita */	
	private void cleanNulls() {

	    int count = 0;
	    int idx = length - 1;
	    while (idx > 0 && (data[idx] == 0x0 || data[idx] == 0x0A)) {
	    	idx--;
	    	count++;
	    }
	    data[++idx] = 0x0A;
	    length -= (count - 1);
	}	
}
