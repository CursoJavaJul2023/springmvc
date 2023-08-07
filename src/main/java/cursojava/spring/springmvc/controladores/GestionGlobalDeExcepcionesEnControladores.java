package cursojava.spring.springmvc.controladores;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @ControllerAdvice
@RestControllerAdvice(
	// assignableTypes = {ControladorGestionClientes.class }
)
public class GestionGlobalDeExcepcionesEnControladores {

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> gestionDeExcepcionesImprevistas(Exception e) {
		
		return ResponseEntity.internalServerError()
				.contentType(MediaType.TEXT_PLAIN)
				.body("Error de ejecución interceptado y registrado");
		
	}

}
