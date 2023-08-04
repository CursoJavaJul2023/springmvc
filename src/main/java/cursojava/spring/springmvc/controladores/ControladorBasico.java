package cursojava.spring.springmvc.controladores;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.entidades.Cuenta;
import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;

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
		path = "/clientes/{nif:\\d{8}[A-Z]}",
		produces = {
			"application/xml",
			"application/json"
		}
	)
	@ResponseBody
	public Cliente buscarClientePorNif(@PathVariable("nif") String elNif) {
		
		
		Cliente cte = new Cliente(elNif, "Nombre", "Apellidos");
		
		return cte;
		
	}
	
	@RequestMapping(
		method = RequestMethod.GET,
		path = "/cuentas/{banco:\\d{4}}-{sucursal:\\d{4}}-{numero:\\d{10}}",
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
		}
	)
	@ResponseBody
	public ResponseEntity<?> buscarCuentaPorCodigo(
			@PathVariable("banco") String banco,
			@PathVariable("sucursal") String sucursal,
			@PathVariable("numero") String numero
			) {
		
//		if(banco.equals("0001")) {
//			return null;
//		}
//		
//		return new Cuenta(new CuentaPk(banco, sucursal, numero), BigDecimal.valueOf(1000.0));
		
		try {
			if(banco.equals("0001")) {
				
				return ResponseEntity.notFound().build();
			}
			
			
			return ResponseEntity.ok(new Cuenta(new CuentaPk(banco, sucursal, numero), BigDecimal.valueOf(1000.0)));
			
		} catch (Exception e) {
			
			return ResponseEntity.internalServerError().build();
		}
		
		
	}
	
	@GetMapping(
		// "/cuentas/codigo?banco=1000&sucursal=1000&numero=1000000000
		path = "/cuentas/codigo",
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	@ResponseBody
	public String buscarCuentaEmpleandoParametrosConsulta(
			@RequestParam(name = "banco", defaultValue = "0010")
			String banco,
			@RequestParam(name = "sucursal", defaultValue = "0002")
			String sucursal,
			@RequestParam(name = "numero", required = false)
			String numero
			) {
		
		return new Cuenta(new CuentaPk(banco, sucursal, numero), BigDecimal.valueOf(1000.0)).toString();
	}
	
	@GetMapping(
		path = "/cabeceras",
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	@ResponseBody
	public String leerCabeceras(
			@RequestHeader(name = "User-Agent", defaultValue = "n/a")
			String cliente,
			@RequestHeader(name = "Accept-Language", defaultValue = "en")
			String idioma,
			@RequestHeader(name = "ClientToken")
			String clientToken			
			) {
		
		return String.format("User-Agent: %s - Accept-Language: %s - ClientToken: %s", cliente, idioma, clientToken);
	}
	
	@PostMapping(
		path = "/cuentas/codigo",
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
		},
		consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE			
		}
	)
	@ResponseBody
	public ResponseEntity<?> buscarCuentaEnviandoDatos(@Valid @RequestBody CuentaPk codigo, BindingResult resultadoValidaciones) {
		
		if(resultadoValidaciones.hasErrors()) {
			
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(new Cuenta(codigo, BigDecimal.valueOf(1000.0)));
	}
	

}







