/**
 * Contiene las constantes simbolicas de los distintos mensajes de TRAP Generados
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class TRAP {

	public static final int END_MARK   =     1;
	
	public static final int HTRAP      =   0x100;  //  256
	public static final int HCODE      =   0x200;  //  512
	public static final int HACCESS    =   0x400;  // 1024
	public static final int HGRAFO     =   0x800;
	public static final int HUNDEF3    =  0x1000;
	public static final int HUNDEF4    =  0x2000;
    public static final int HUNDEF5    =  0x4000;
	public static final int HUNDEF     =  0x8000;

	// Mascara del grupo
	
	public static final int TRAP       =    0x01;
	public static final int CODE       =    0x02;
	public static final int ACCESS     =    0x04;
	public static final int UNDEF2     =    0x08;
	public static final int UNDEF3     =    0x10;
	public static final int UNDEF4     =    0x20;
    public static final int UNDEF5     =    0x40;
	public static final int UNDEF      =    0x80;

	// Mascara de TRAP
	
	public static final int MODULE      =   0x02;
	public static final int PARAGRAPH   =   0x04;
	public static final int CALL        =   0x08;
	public static final int SQL         =   0x10;
	public static final int CICS        =   0x20;
	public static final int CUSTOM      =   0x40;
	
	// Mascara de CODE
	
	public static final int WORKINGS   =     0x02;
	public static final int BLOQUE     =     0x04;
	public static final int PARR       =     0x08;
	public static final int PERFORM    =     0x10;
	public static final int ENDPERFORM =     0x11;
	public static final int INLINE     =     0x12;
	public static final int FIRST      =     0x20;
	public static final int IF         =     0x40;
	public static final int EVAL       =     0x80;
	public static final int SEARCH     =     0x100;
	
	// Mascara de ACCESS
	
    public static final int OPEN      =   0x01;
    public static final int CLOSE     =   0x02;
	public static final int INSERT    =   0x04;
	public static final int READ      =   0x08;	
	public static final int UPDATE    =   0x10;
	public static final int DELETE    =   0x20;
	public static final int SELECT    =   0x40;
	public static final int TOTAL     =   0x80;
	

	// Valores de code
	
	public static final int WORKING     = HCODE + WORKINGS;  // 514
	public static final int BLOCK       = HCODE + BLOQUE;    // 516
	public static final int IN_PARR     = HCODE + PARR;      // 518
	public static final int FIRST_PARR  = HCODE + FIRST;
	public static final int END_IF      = HCODE + IF;
	public static final int END_EVAL    = HCODE + EVAL;
    public static final int END_SEARCH  = HCODE + SEARCH;	
	
	// Valores BEG
	
	public static final int BEG_MODULE     = HTRAP + MODULE;    // 258
	public static final int BEG_PERFORM    = HTRAP + PARAGRAPH; // 260
	public static final int BEG_CALL       = HTRAP + CALL;
	public static final int BEG_SQL        = HTRAP + SQL;
	public static final int BEG_CICS       = HTRAP + CICS;
	public static final int BEG_CUSTOM     = HTRAP + CUSTOM;
	
	// Valores END
	
	public static final int END_MODULE        = BEG_MODULE    + END_MARK; // 259
	public static final int END_PERFORM       = BEG_PERFORM   + END_MARK;
	public static final int END_CALL          = BEG_CALL      + END_MARK;
	public static final int END_SQL           = BEG_SQL       + END_MARK;
	public static final int END_CICS          = BEG_CICS      + END_MARK;
	public static final int END_CUSTOM        = BEG_CUSTOM    + END_MARK;
	
    // Valores ACESS 

	public static final int ACC_OPEN     = HACCESS + OPEN;   // 1025
	public static final int ACC_CLOSE    = HACCESS + CLOSE;  // 1026
	public static final int ACC_INSERT   = HACCESS + INSERT;
	public static final int ACC_READ     = HACCESS + READ;
	public static final int ACC_UPDATE   = HACCESS + UPDATE;
	public static final int ACC_DELETE   = HACCESS + DELETE;
	public static final int ACC_SELECT   = HACCESS + SELECT;

	
	// Usados en la libreria
	public static final int COVER       =  0x100;
	public static final int PERS        =  0x200;
	public static final int PARRWRK     =  0x400;
	public static final int BEG_SESSION =   0x00;
	public static final int END_SESSION =   0x01;

	// Usados para el grafo
	
	public static final int EVALUATE = HGRAFO + 2;
	public static final int WHEN     = HGRAFO + 3;
	public static final int ELSE     = HGRAFO + 4;
	public static final int ATEND    = HGRAFO + 8;
	
	// Usados por SDPTRAPx

	public static final int FLG_VERIFY    =  0;
	public static final int FLG_PROFILE   =  1;
	public static final int FLG_SUSPEND   =  2;
	public static final int FLG_MODULE    =  4;
	public static final int FLG_PARAGRAPH =  5;	
	public static final int FLG_PERFORM   =  6;
	public static final int FLG_CALL      =  7;
	public static final int FLG_SQL       =  8;
	public static final int FLG_CICS      =  9;
	
	public static final int FLG_UNDEF     = 15;
	
	private TRAP() {
		
	}
}
