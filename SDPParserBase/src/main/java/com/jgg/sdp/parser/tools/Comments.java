package com.jgg.sdp.parser.tools;

import com.jgg.sdp.module.base.Module;

public class Comments {

   int lastLine;
   
   StringBuilder data;
   Module        module;
   
   public void setModule(Module module) {
       this.module = module;
   }
   public void init(String txt, int line, boolean inCode) {
       data = new StringBuilder(txt);
   }
   
   public void append(String txt) {
       data.append(txt);
   }

   public void process() {
       
   }
}
