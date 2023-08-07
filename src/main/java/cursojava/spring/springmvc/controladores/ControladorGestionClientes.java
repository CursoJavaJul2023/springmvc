package cursojava.spring.springmvc.controladores;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;

@RestController // = @Controller + @ResponseBody
@RequestMapping(
	path = "/modelobanco/v1/clientes"
)
// @Slf4j
public class ControladorGestionClientes {
	
	@Autowired
	private RepositorioCliente repoCliente;
	
	private static Logger logger = LoggerFactory.getLogger(ControladorGestionClientes.class);
		
	@GetMapping(
//		path = "/modelobanco/v1/clientes/{nif:\\d{8}[A-Z]}",
		path = "{nif:\\d{8}[A-Z]}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> buscarPorNif(@PathVariable("nif") String nif) {
				
		try {
			
			logger.debug("Buscando cliente con nif {}", nif);			
			Cliente cliente = repoCliente.buscarPorNif(nif);
			
			if(cliente == null) {
				
				logger.debug("Cliente no encontrado");
				return ResponseEntity.notFound().build();
			}
			
			logger.debug("Cliente encontrado");
			return ResponseEntity.ok(cliente);
			
		} catch (Exception e) {
			
			logger.error("Error al buscar por nif", e);
			
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping(
//		path = "/modelobanco/v1/clientes",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> buscarTodos() {
		
		try {
			
			List<Cliente> listaClientes = repoCliente.buscarTodos();
			
			return ResponseEntity.ok(listaClientes);
			
		} catch (Exception e) {
		
			logger.error("No pudo cargar los clientes", e);
			
			return ResponseEntity.internalServerError().build();
		}
		
	}

	@PostMapping(
//		path = "/modelobanco/v1/clientes",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)	
	public ResponseEntity<?> alta(@Valid @RequestBody Cliente cliente, BindingResult resultadoValidaciones) {		
				
		try {
			
			if(resultadoValidaciones.hasErrors()) {
				logger.warn("Cliente con validaciones incorrectas {}", cliente);
				return ResponseEntity.badRequest().build();
			}
			
			repoCliente.alta(cliente);
			
			// Es correcto
			// return ResponseEntity.ok().build();
			
			return ResponseEntity.created(
					// No es portable, hay que externalizarlo
					URI.create("http://localhost:8080/springmvc/modelobanco/v1/clientes/" + cliente.getNif())
				).body(cliente);
			
		} catch (Exception e) {
			
			logger.error("No pudo crear cliente", e);
			
			return ResponseEntity.internalServerError().build();
		}		
	}
	
	@PutMapping(
//		path = "/modelobanco/v1/clientes",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)	
	public ResponseEntity<?> modificar(@Valid @RequestBody Cliente cliente, BindingResult resultadoValidaciones) {
		
		try {
			
			if(resultadoValidaciones.hasErrors()) {
				logger.warn("Cliente con validaciones incorrectas {}", cliente);
				return ResponseEntity.badRequest().build();
			}
			
			repoCliente.modificar(cliente);
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			
			logger.error("No se pudo modificar el cliente", e);
			
			return ResponseEntity.internalServerError().build();			
		}
		
	}
	
	@DeleteMapping(
//		path = "/modelobanco/v1/clientes/{nif:\\d{8}[A-Z]}",
		path = "{nif:\\d{8}[A-Z]}",			
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> borrar(@PathVariable("nif") String nif) {
		
		try {
			
			Cliente cliente = repoCliente.buscarPorNif(nif);
			
			if(cliente == null) {
				return ResponseEntity.notFound().build();
			}
			
			repoCliente.borrar(cliente);			
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			
			logger.error("No se pudo borrar cliente", e);
			
			return ResponseEntity.internalServerError().build();
		}
		
	}

}
