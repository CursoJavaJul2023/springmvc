package cursojava.jpahibernate.orm.modelobanco.repositorios;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cursojava.jpahibernate.orm.modelobanco.entidades.Movimiento;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@Transactional(rollbackFor = NegocioException.class)
public interface RepositorioMovimiento {

	void alta(Movimiento mov) throws NegocioException;

	@Transactional(readOnly = true)
	Movimiento buscarPorId(Integer id) throws NegocioException;

	void modificar(Movimiento mov) throws NegocioException;

	@Transactional(readOnly = true)
	List<Movimiento> buscarTodos() throws NegocioException;

}
