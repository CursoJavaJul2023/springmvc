package cursojava.spring.springmvc.controladores;

import javax.validation.Valid;

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
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@RestController // = @Controller + @ResponseBody
@RequestMapping(
	path = "/modelobanco/v1/clientes"
)
public class ControladorGestionClientes {
	
	@Autowired
	private RepositorioCliente repoCliente;
		
	@GetMapping(
//		path = "/modelobanco/v1/clientes/{nif:\\d{8}[A-Z]}",
		path = "{nif:\\d{8}[A-Z]}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> buscarPorNif(@PathVariable("nif") String nif) {
		
		try {
			Cliente cliente = repoCliente.buscarPorNif(nif);
			
			if(cliente == null) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(cliente);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping(
//		path = "/modelobanco/v1/clientes",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> buscarTodos() {
		
		return ResponseEntity.ok().build();
		
	}

	@PostMapping(
//		path = "/modelobanco/v1/clientes",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)	
	public ResponseEntity<?> alta(@Valid @RequestBody Cliente cliente, BindingResult resultadoValidaciones) {
		
		
		return ResponseEntity.ok().build();		
	}
	
	@PutMapping(
//		path = "/modelobanco/v1/clientes",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)	
	public ResponseEntity<?> modificar(@Valid @RequestBody Cliente cliente, BindingResult resultadoValidaciones) {
		
		return ResponseEntity.ok().build();
		
	}
	
	@DeleteMapping(
//		path = "/modelobanco/v1/clientes/{nif:\\d{8}[A-Z]}",
		path = "{nif:\\d{8}[A-Z]}",			
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> borrar(@PathVariable("nif") String nif) {
		
		return ResponseEntity.ok().build();
		
	}

}
