package com.cris.ProyectoEmpleo.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cris.ProyectoEmpleo.Model.Perfil;
import com.cris.ProyectoEmpleo.Model.Usuario;
import com.cris.ProyectoEmpleo.Model.Vacante;
import com.cris.ProyectoEmpleo.Service.InterCategoriaService;
import com.cris.ProyectoEmpleo.Service.InterUsuarioService;
import com.cris.ProyectoEmpleo.Service.InterVacantesServic;

@Controller
public class HomeController {
	
		@Autowired
		private InterVacantesServic ivacantesservice;
		
		@Autowired
		private InterUsuarioService iusuarioservice;

		@Autowired
		private InterCategoriaService icategoriaservice;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		//login
		@GetMapping("/login" )
		public String mostrarLogin() {
		return "formLogin";
		}
		
		@GetMapping("/logout")
		public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler =
		new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
		}

		
		@GetMapping("/index")
		/*	PARA RECUPERAR EL NOMBRE DE USUARIO QUE INICIO SESION Y EL ROL ASIGNADO	*/
		public String mostrarIndex(Authentication au , HttpSession session) {
			String name = au.getName();
			
			System.out.println("Usuario : "+name); 
			
			for (GrantedAuthority rol : au.getAuthorities()) {
				System.out.println("Rol : "+ rol.getAuthority());
			}
			
			if(session.getAttribute("usuario") == null) {			
			Usuario u = iusuarioservice.buscarUsuarioPorUsername(name);
			u.setPassword(null); 
			System.out.println("Usuario : "+u); 
			session.setAttribute("usuario", u); 
			}
			
			
			return "redirect:/";
		}
		
		/*	INICIO	*/
		@GetMapping("/")
		public String home(Model model) {
			return "home";
		}
		/*	Crea usuario	*/
		@GetMapping("/signup")
		public String registrarse(Usuario usuario) {
			return "formRegistro";
		}
		
		@PostMapping("/signup")
		public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
			
			//contraseña en texto plano
			String pwplano = usuario.getPassword();
			//contraseña encriptada
			String pwencrip = passwordEncoder.encode(pwplano);
 			
			usuario.setPassword(pwencrip);
			usuario.setEstatus(1); // Activado por defecto
			usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
			// Creamos el Perfil que le asignaremos al usuario nuevo
			Perfil perfil = new Perfil();
			perfil.setId(3); // Perfil USUARIO
			usuario.agregar(perfil);
			/**
			 * Guardamos el usuario en la base de datos. perfin se guarda automaticamente
			 */
			iusuarioservice.guardar(usuario); 
			attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");
			return "redirect:/login";
		}
		
		@GetMapping("/about")
		public String mostrarAcerca() {			
			return "acerca";
		}
		
		@GetMapping("/search")
		public String buscar(@ModelAttribute("search") Vacante vacante , Model model) {
			System.out.println("Buscando por "+vacante);
			ExampleMatcher matcher = ExampleMatcher.matching().
			withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
			Example<Vacante> example = Example.of(vacante,matcher);
			List<Vacante> lista = ivacantesservice.buscarByExample(example);
			model.addAttribute("vacante",lista);
			return "home";
		}
		
		/*	Genericos	*/
		@ModelAttribute
		public void setGenericos(Model model) {
			Vacante vsearch = new Vacante();
			vsearch.reset();
			model.addAttribute("vacante", ivacantesservice.buscardestacadas()); 
			model.addAttribute("categorias",icategoriaservice.buscarxtodas());
			model.addAttribute("search",vsearch);
		}
		
		/*		*/
		@GetMapping("/bcrypt/{texto}")
		@ResponseBody
		public String encriptar(@PathVariable("texto") String texto) {
			return texto + "Encriptado en Bcrypt : " + passwordEncoder.encode(texto);
		}
		
		/*	initBinder para Strings si los detecta vacios en el data biding los settea a null	*/
		@InitBinder
		public void initBinder(WebDataBinder binder) {
			binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		}
}
