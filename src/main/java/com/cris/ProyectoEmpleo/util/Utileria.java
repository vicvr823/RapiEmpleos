package com.cris.ProyectoEmpleo.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {

	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		// Obtenemos el nombre original del archivo.
		String nombreOriginal = multiPart.getOriginalFilename();
		nombreOriginal = nombreOriginal.replace(" ", "-");
		String nombreFinal = ramdomAlpha(8) + nombreOriginal;
		try {
		// Formamos el nombre del archivo para guardarlo en el disco duro.
		File imageFile = new File(ruta + nombreFinal);
		System.out.println("Archivo: " + imageFile.getAbsolutePath());
		//Guardamos fisicamente el archivo en HD.
		multiPart.transferTo(imageFile);
		return nombreFinal;
		} catch (IOException e) {
		System.out.println("Error " + e.getMessage());
		return null;
		}
	}

	/*
	 * MEtodo para cadena aleatoria de longitud
	 * */
	
	public static String ramdomAlpha(int count) {
		
		String caracter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		
		while (count -- !=0) {
			
			int charac = (int) (Math.random() * caracter.length());
			builder.append(caracter.charAt(charac));
			
		}
		
		return builder.toString();
	}
	
}
