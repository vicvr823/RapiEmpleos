package com.cris.ProyectoEmpleo.Controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cris.ProyectoEmpleo.Model.Solicitud;
import com.cris.ProyectoEmpleo.Model.Usuario;
import com.cris.ProyectoEmpleo.Model.Vacante;
import com.cris.ProyectoEmpleo.Service.InterSolicitudesService;
import com.cris.ProyectoEmpleo.Service.InterUsuarioService;
import com.cris.ProyectoEmpleo.Service.InterVacantesServic;
import com.cris.ProyectoEmpleo.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	

	@Value("${empleos.ruta.cv}")
	private String ruta;
		
	@Autowired
	private InterSolicitudesService serviceSolicitudes;

    @Autowired
    private InterVacantesServic serviceVacantes;
	    
	@Autowired
	private InterUsuarioService serviceUsuarios;

	@GetMapping("/index")
	public String mostrarIndexPaginado(Model model , Pageable page) {
		Page<Solicitud> lista = serviceSolicitudes.buscarTodas(page);
		model.addAttribute("solicitudes", lista);
		return "solicitudes/listSolicitudes";
		
	}
    

	@GetMapping("/create/{idVacante}")
	public String crear(Solicitud solicitud, @PathVariable Integer idVacante, Model model) {
		// Traemos los detalles de la Vacante seleccionada para despues mostrarla en la vista
		Vacante vacante = serviceVacantes.buscarxid(idVacante);
		model.addAttribute("vacante", vacante);
		return "solicitudes/formSolicitud";
	}
	
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
			@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, Authentication authentication) {	
		
		// Recuperamos el username que inicio sesión
		String username = authentication.getName();
		
		if (result.hasErrors()){
			
			System.out.println("Existieron errores");
			return "solicitudes/formSolicitud";
		}	
		
		if (!multiPart.isEmpty()) {
			//String ruta = "/empleos/files-cv/"; // Linux/MAC
			//String ruta = "c:/empleos/files-cv/"; // Windows
			String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreArchivo!=null){ // El archivo (CV) si se subio				
				solicitud.setArchivo(nombreArchivo); // Asignamos el nombre de la imagen
			}	
		}

		// Buscamos el objeto Usuario en BD	
		Usuario usuario = serviceUsuarios.buscarUsuarioPorUsername(username);		
		
		solicitud.setUsuario(usuario); // Referenciamos la solicitud con el usuario 
		solicitud.setFecha(new Date());
		// Guadamos el objeto solicitud en la bd
		serviceSolicitudes.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Gracias por enviar tu CV!");
			
		//return "redirect:/solicitudes/index";
		return "redirect:/";		
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes) {
		
		// Eliminamos la solicitud.
		serviceSolicitudes.eliminar(idSolicitud);
			
		attributes.addFlashAttribute("msg", "La solicitud fue eliminada!.");
		//return "redirect:/solicitudes/index";
		return "redirect:/solicitudes/indexPaginate";
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
