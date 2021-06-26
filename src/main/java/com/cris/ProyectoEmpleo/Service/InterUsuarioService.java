package com.cris.ProyectoEmpleo.Service;

import java.util.List;
import com.cris.ProyectoEmpleo.Model.Usuario;


public interface InterUsuarioService {
	void guardar(Usuario usuario);
	List<Usuario> buscarxtodas();
	Usuario buscarxid(Integer id);
	List<Usuario> buscarRegistrados();
	void eliminar(Integer idUsuario);
	Usuario buscarUsuarioPorUsername(String username);
	int bloquear(int idUsuario);
	int activar(int idUsuario);
	
	int conceder(int idUsuario);
	int desconceder(int idUsuario);
}
