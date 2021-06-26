package com.cris.ProyectoEmpleo.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cris.ProyectoEmpleo.Model.Vacante;
import com.cris.ProyectoEmpleo.Service.InterCategoriaService;
import com.cris.ProyectoEmpleo.Service.InterVacantesServic;
import com.cris.ProyectoEmpleo.util.Utileria;

@Controller
@RequestMapping(value="/vacantes")
public class VacantesController {
	
	@Value("${empleos.ruta.imagenes}")
	private String ruta;

	@Autowired
	private InterVacantesServic ivacantesservice ;
	
	//para inyectar
	@Autowired
	private InterCategoriaService icategoriaservice;
	
	@GetMapping(value = "/index")
	public String mostrarIndexPaginado(Model model, Pageable page) {
	Page<Vacante> lista = ivacantesservice.buscarTodas(page);
	model.addAttribute("vacante", lista);
	return "vacantes/listVacantes";
	}

	
	//crecion de vacante
	@GetMapping("/create")
	public String crear(Vacante vacante , Model model) {
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")
	public String guardar(@ModelAttribute Vacante vacante , BindingResult result , RedirectAttributes attributes , 
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		
		if (result.hasErrors()) { 
			for (ObjectError error: result.getAllErrors()){ System.out.println("Ocurrio un error: " + error.getDefaultMessage()); 
		}
			return "vacantes/formVacante"; 
		} 
		
		if (!multiPart.isEmpty()) {
			//String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null){ // La imagen si se subio
			// Procesamos la variable nombreImagen
			vacante.setImagen(nombreImagen);
			}
			
		}
		ivacantesservice.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro Guardado");
 		System.out.println("Vacante : " + vacante);
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante , Model model) {
		Vacante vacante = ivacantesservice.buscarxid(idVacante); 
		model.addAttribute("vacante",vacante);
		return "vacantes/formVacante";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante , Model model) {
		Vacante vacante = ivacantesservice.buscarxid(idVacante);	
		System.out.println("IdVacante : " + vacante);
		model.addAttribute("vacante",vacante);
		return "detalle";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante , Model model , RedirectAttributes attributes) {
		System.out.println("Borrando vacante " + idVacante);
		ivacantesservice.eliminar(idVacante); 
		attributes.addFlashAttribute("msg" , "La vacante fue eliminada");
		return "redirect:/vacantes/index";
	}  
	
	/*	datos comunes para el controlador	*/
	@ModelAttribute
	public void setGenerico(Model model) {
		model.addAttribute("categorias", icategoriaservice.buscarxtodas());
	}
	
	
	/* para la fecha */
	@InitBinder 
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); 
	}

}
