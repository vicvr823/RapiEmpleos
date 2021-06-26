package com.cris.ProyectoEmpleo.Service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cris.ProyectoEmpleo.Model.Usuario;
import com.cris.ProyectoEmpleo.Service.InterUsuarioService;
import com.cris.ProyectoEmpleo.repository.UsuariosRepository;

@Service
public class UsuarioServiceJpa implements InterUsuarioService {

	@Autowired
	private UsuariosRepository repoUsuario;
	
	@Override
	public void guardar(Usuario usuario) {
		repoUsuario.save(usuario);
	}

	@Override
	public List<Usuario> buscarxtodas() {
		return repoUsuario.findAll();
	}

	@Override
	public Usuario buscarxid(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer idUsuario) {
		repoUsuario.deleteById(idUsuario);
 	}

	@Override
	public Usuario buscarUsuarioPorUsername(String username) {
		return repoUsuario.findByUsername(username); 
	}

	@Override
	public List<Usuario> buscarRegistrados() {
		return repoUsuario.findByFechaRegistroNotNull();
	}

	@Transactional
	@Override
	public int bloquear(int idUsuario) {
		int rows = repoUsuario.lock(idUsuario);
		return rows;
	}

	@Transactional
	@Override
	public int activar(int idUsuario) {
		int rows = repoUsuario.unlock(idUsuario);
		return rows;
	}

	@Transactional
	@Override
	public int conceder(int idUsuario) {
		int rows = repoUsuario.supervisor(idUsuario);
		return rows;
	}

	@Transactional
	@Override
	public int desconceder(int idUsuario) {
		int rows = repoUsuario.usuario(idUsuario);
		return rows;
	}
	
}
