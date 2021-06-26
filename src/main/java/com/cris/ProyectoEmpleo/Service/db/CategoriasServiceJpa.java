package com.cris.ProyectoEmpleo.Service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cris.ProyectoEmpleo.Model.Categoria;
import com.cris.ProyectoEmpleo.Service.InterCategoriaService;
import com.cris.ProyectoEmpleo.repository.CategoriasRepository;

@Service
@Primary
public class CategoriasServiceJpa implements InterCategoriaService {

	@Autowired
	private CategoriasRepository repoCategorias;

	@Override
	public void guardar(Categoria categoria) {
		repoCategorias.save(categoria);
	}
 
	@Override
	public List<Categoria> buscarxtodas() {
		return repoCategorias.findAll();
	}

	@Override
	public Categoria buscarxid(Integer id) {
		Optional<Categoria> op = repoCategorias.findById(id);
		if(op.isPresent()) {
			return op.get(); 
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		repoCategorias.deleteById(idCategoria);
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		return repoCategorias.findAll(page); 
	}

}
