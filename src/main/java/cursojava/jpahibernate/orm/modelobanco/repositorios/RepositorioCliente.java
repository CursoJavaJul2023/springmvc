package cursojava.jpahibernate.orm.modelobanco.repositorios;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@Transactional(rollbackFor = NegocioException.class)
public interface RepositorioCliente {

	void alta(Cliente cte) throws NegocioException;

	@Transactional(readOnly = true)
	Cliente buscarPorNif(String nif) throws NegocioException;

	void modificar(Cliente cte) throws NegocioException;

	@Transactional(readOnly = true)
	List<Cliente> buscarTodos() throws NegocioException;
	
	@Transactional(readOnly = true)
	List<Cliente> cargarTodosConCuentasAsignadas() throws NegocioException;

}
