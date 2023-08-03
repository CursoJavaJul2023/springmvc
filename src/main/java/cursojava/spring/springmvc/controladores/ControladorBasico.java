package cursojava.spring.springmvc.controladores;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControladorBasico {
	
	@RequestMapping(
		path = "/prueba",
		method = RequestMethod.GET,
		produces = "text/plain"
	)
	@ResponseBody
	public String prueba() {
		
		return "Funciona " + LocalDateTime.now();
	}
	
	@RequestMapping(
		method = RequestMethod.GET,
		path = "/consulta"
	)
	public String empleoConVista(Model modelo) {
		
		modelo.addAttribute("mensaje", "Cadena que se envía desde el controlador");
		modelo.addAttribute("fecha", new Date());
		
		return "consulta.jsp";
	}
	
	@RequestMapping(
		method = RequestMethod.GET,
		path = "/clientes/{nif:\\d{8}[A-Z]}"
	)
	@ResponseBody
	public void buscarClientePorNif(@PathVariable("nif") String elNif) {
		
		System.out.println("Nif: " + elNif);
		
	}
	
	

}







