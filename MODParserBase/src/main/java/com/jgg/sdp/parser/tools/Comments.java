package com.jgg.sdp.parser.tools;

import java.util.regex.*;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.Comment;
import com.jgg.sdp.parser.base.ParserInfo;

public class Comments {

   int     lastLine = -2;
   int     currLine = 0;
   boolean block    = false;
   
   StringBuilder data;
   Module        module;
   Comment       comment = null;
   Pattern       pattern = Pattern.compile(" [a-zA-Z0-9.]+");

   private ParserInfo info = ParserInfo.getInstance();
   
   public void setModule(Module module) {
       this.module = module;
       comment = module.getComment();
   }
   public void init(String txt, int line) {
       data = new StringBuilder(txt);
       currLine = line;
   }
   
   public void append(String txt) {
       data.append(txt);
   }

   /*
    * Asumimos que es una documentacion si contiene al menos una palabra
    */
   public void process(int line) {
	   checkRuleComment(line);
	   comment.incLines();
	   
       String aux = data.toString().trim();
       if (pattern.matcher(aux).find()) {
    	   comment.incDocs();
       }
       else {
    	   comment.incDecorators();
       }
       
       if (line - lastLine == 1) {
    	   block = true;
       }
       else {
    	   if (block) {
    		   comment.incBlocks();
    		   block = false;
    	   }
       }
       
   }
   
   private void checkRuleComment(int line) {
	   info.rules.checkComment(data.toString().trim(), line + 1);	   
   }
}
