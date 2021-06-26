package com.cris.ProyectoEmpleo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cris.ProyectoEmpleo.Model.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByUsername(String username);
	List<Usuario> findByFechaRegistroNotNull();
	
	@Modifying
    @Query("UPDATE Usuario u SET u.estatus=0 WHERE u.id = :paramIdUsuario")
    int lock(@Param("paramIdUsuario") int idUsuario);
	
	@Modifying
    @Query("UPDATE Usuario u SET u.estatus=1 WHERE u.id = :paramIdUsuario")
    int unlock(@Param("paramIdUsuario") int idUsuario);
	
	@Modifying
	@Query(value = "UPDATE usuarioperfil up join usuarios u  on up.idUsuario = u.id SET idPerfil = 1 WHERE u.id = :paramIdUsuario" , nativeQuery = true)
    int supervisor(@Param("paramIdUsuario") int idUsuario);
	
	@Modifying
	@Query(value = "UPDATE usuarioperfil up join usuarios u  on up.idUsuario = u.id SET idPerfil = 3 WHERE u.id = :paramIdUsuario" , nativeQuery = true)
    int usuario(@Param("paramIdUsuario") int idUsuario);
}
