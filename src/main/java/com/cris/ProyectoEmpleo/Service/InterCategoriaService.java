 package com.cris.ProyectoEmpleo.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cris.ProyectoEmpleo.Model.Categoria;

public interface InterCategoriaService {

	void guardar(Categoria categoria);
	List<Categoria> buscarxtodas();
	Categoria buscarxid(Integer id);
	
	void eliminar(Integer idCategoria);
	Page<Categoria> buscarTodas(Pageable page);
}
