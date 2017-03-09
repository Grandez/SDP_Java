/**
 * Contiene las constantes simbolicas para acceder a la tabla de log y sus mensajes
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class LOG {

    public static final int INFO    =   1;
    public static final int WARNING =   2;
    public static final int ALERT   =   3;
    public static final int SEVERE  =   9;
    
    public static final int MOD_NEW     =   1;
    public static final int MOD_COMP    =   2;
    public static final int MOD_VERSION =   3;
    
    // 1xxx son alertas
    
    public static final int SES_ELAPSED = 1001;
    public static final int SES_CPU     = 1002;
    public static final int SES_SUSPEND = 1003;
    public static final int SES_MEDIA   = 1004;
    
    public static final int SES_LEIDO_MENOS   = 1011;
    public static final int SES_LEIDO_MAS     = 1012;    
    public static final int SES_ESCRITO_MENOS = 1013;
    public static final int SES_ESCRITO_MAS   = 1014;

    public static final int USR_ADDED         = 2001;
    public static final int ADMIN_ADDED       = 2002;
    public static final int USR_MOD           = 2003;
    
    public static final int CFG_CHANGED       = 2101;
    
    // Mensajes de la Web
    
    public static final int WEB_BAD_USER       = 10001;
    public static final int WEB_NAME_REQUIRED  = 10002;
    public static final int WEB_AP_REQUIRED    = 10003;    
    public static final int WEB_MAIL_REQUIRED  = 10004;
    public static final int WEB_MAIL_INVALID   = 10005;    
    public static final int WEB_ROLE_INVALID   = 10006;
    public static final int WEB_USER_DUP       = 10007;

    // Bloques de mensajes de Web
    // Se recuperan emepzando por este codigo
    // y siguiendo ordenadamente hasta que no hay datos
    public static final int WEB_TXT_ARBOL      = 10100;
    
}
