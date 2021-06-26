package com.cris.ProyectoEmpleo.Service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cris.ProyectoEmpleo.Model.Solicitud;
import com.cris.ProyectoEmpleo.Service.InterSolicitudesService;
import com.cris.ProyectoEmpleo.repository.SolicitudesRepository;



@Service
public class SolicitudesServiceJpa implements InterSolicitudesService {

	@Autowired
	private SolicitudesRepository repoSolicitudes;
	
	@Override
	public void guardar(Solicitud solicitud) {
		repoSolicitudes.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		repoSolicitudes.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return repoSolicitudes.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = repoSolicitudes.findById(idSolicitud);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return repoSolicitudes.findAll(page);
	}

}
