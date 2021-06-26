package com.cris.ProyectoEmpleo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cris.ProyectoEmpleo.Model.Usuario;
import com.cris.ProyectoEmpleo.Service.InterUsuarioService;

@Controller
@RequestMapping(value="/usuarios")
public class UsuarioController {
	
	@Autowired
	private InterUsuarioService iusuarioservice;
	
	/*	MUESTRA LOS USUARIOS	*/
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Usuario> lista = iusuarioservice.buscarxtodas();
		model.addAttribute("usuarios" , lista);	
    	return "usuarios/listUsuarios";
	}
    /*	ELIMINA		*/
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		    	
    	iusuarioservice.eliminar(idUsuario);
    	attributes.addFlashAttribute("msg", "El usuario fue eliminado!.");
		return "redirect:/usuarios/index";
	}
    
    @GetMapping("/unlock/{id}")
   	public String activar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
    	iusuarioservice.activar(idUsuario);
   		attributes.addFlashAttribute("msg", "El usuario fue activado y ahora tiene acceso al sistema.");		
   		return "redirect:/usuarios/index";
   	}
       

   	@GetMapping("/lock/{id}")
   	public String bloquear(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
   		iusuarioservice.bloquear(idUsuario);
   		attributes.addFlashAttribute("msg", "El usuario fue bloqueado y no tendra acceso al sistema.");		
   		return "redirect:/usuarios/index";
   	}
    
   	@GetMapping("/conceder/{id}")
   	public String concede(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
   		iusuarioservice.conceder(idUsuario);
   		attributes.addFlashAttribute("msg", "Fue cambiado de rango a SUPERVISOR.");		
   		return "redirect:/usuarios/index";
   	}
   	
   	@GetMapping("/desconceder/{id}")
   	public String desconcede(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
   		iusuarioservice.desconceder(idUsuario);
   		attributes.addFlashAttribute("msg", "Fue cambiado de rango a USUARIO.");		
   		return "redirect:/usuarios/index";
   	}
}
 