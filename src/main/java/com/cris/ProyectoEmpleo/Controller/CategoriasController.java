package com.cris.ProyectoEmpleo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cris.ProyectoEmpleo.Model.Categoria;
import com.cris.ProyectoEmpleo.Model.Vacante;
import com.cris.ProyectoEmpleo.Service.InterCategoriaService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

	@Autowired
	private InterCategoriaService icategoriaservice;
	
	@GetMapping("/index")
	public String mostrarIndexPaginado(Model model, Pageable page) {
	Page<Categoria> lista = icategoriaservice.buscarTodas(page);
	model.addAttribute("categoria", lista);
	return "categorias/listCategoria";
	}

	
	/*	CREAR	*/
	@RequestMapping(value="/create" , method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}
	
	/*	GUARDAR	*/
	@RequestMapping(value="/save" , method = RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result , RedirectAttributes attributes) {
		
		if (result.hasErrors()) { 
			for (ObjectError error: result.getAllErrors()){ System.out.println("Ocurrio un error: " + error.getDefaultMessage()); 
		}
			return "categorias/formCategoria"; 
		} 
		icategoriaservice.guardar(categoria); 
		attributes.addFlashAttribute("msg", "Registro Guardado");
		System.out.println("Categoria "+categoria);
		return "redirect:/categorias/index";
	}
	/*	ELIMINAR CATEGORIA	*/
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria , Model model , RedirectAttributes attributes) {
		System.out.println("Borrando categoria " + idCategoria);
		icategoriaservice.eliminar(idCategoria); 
		attributes.addFlashAttribute("msg" , "La Categoria fue eliminada");
		return "redirect:/categorias/index";
	} 
	/*	EDITAR CATEGORIA	*/
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria , Model model) {
		Categoria categoria = icategoriaservice.buscarxid(idCategoria); 
		model.addAttribute("categoria",categoria);
		return "categorias/formCategoria";
	}
}
