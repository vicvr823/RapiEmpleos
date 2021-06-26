package com.cris.ProyectoEmpleo.Service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cris.ProyectoEmpleo.Model.Vacante;
import com.cris.ProyectoEmpleo.Service.InterVacantesServic;
import com.cris.ProyectoEmpleo.repository.VacantesRepository;
@Service
@Primary
public class VacantesServiceJpa implements InterVacantesServic {

	@Autowired
	private VacantesRepository repoVacantes;
	
	@Override
	public List<Vacante> buscartodo() {
		return repoVacantes.findAll();
	}

	@Override
	public Vacante buscarxid(Integer id) {
		Optional<Vacante> op = repoVacantes.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		repoVacantes.save(vacante);
	}

	@Override
	public List<Vacante> buscardestacadas() {
		
		return repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1,"Aprobada"); 
	}

	@Override
	public void eliminar(Integer idVacante) {
		repoVacantes.deleteById(idVacante);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return repoVacantes.findAll(example);  
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return repoVacantes.findAll(page); 
	}

}
