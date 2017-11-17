package com.jgg.sdp.module.work;

import java.util.regex.*;

public class CommentLine {
   private int    line = 0;
   private String comment;
   private String rawComment;
   private boolean doc = false;
   
   public CommentLine(String txt, int line) {
	   rawComment = txt;
	   comment = txt.trim();
	   this.line = line;
	   Pattern       pattern = Pattern.compile(" [a-zA-Z0-9.]+");
	   if (pattern.matcher(txt).find()) doc = true;
   }
   
   public int     getLine()       { return line;             }
   public String  getComment()    { return comment;          }
   public String  getRawComment() { return rawComment;       }   
   public boolean isDecorator()   { return doc == false;     }
   public boolean isComment()     { return doc == true;      }
   public int     length()        { return comment.length(); }
   
   @Override
   public String toString()       { return comment;       }
}
