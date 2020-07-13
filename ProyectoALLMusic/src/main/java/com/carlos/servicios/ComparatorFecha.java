package com.carlos.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.carlos.datos.albumes.Album;

@Service
public class ComparatorFecha implements Comparator<Album> {

        @Override
        public int compare(Album a, Album b) {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
				return sdf.parse(b.getFecha_salida()).compareTo(sdf.parse(a.getFecha_salida()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
        }
        
}
