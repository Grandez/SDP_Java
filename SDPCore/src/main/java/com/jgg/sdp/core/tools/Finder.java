/**
 * Implementa una utilidad para buscar y filtar listas de ficheros
 * con wildcards 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.tools;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class Finder  extends SimpleFileVisitor<Path> {

    	private ArrayList<Path> files = new ArrayList<Path>();
    	
        private final PathMatcher matcher;

        /**
         * Crea un nuevo Finder con el patron indicado
         *
         * @param pattern El patron a aplicar
         */
        public Finder(String pattern) {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern);
        }

        /**
         * Realiza la busqueda
         *
         * @param file El fichero a buscar
         */
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                files.add(file);
            }
        }

        public ArrayList<Path> getFiles() {
        	return this.files;
        }
        
        @Override
        public FileVisitResult visitFile(Path file,
                BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }
