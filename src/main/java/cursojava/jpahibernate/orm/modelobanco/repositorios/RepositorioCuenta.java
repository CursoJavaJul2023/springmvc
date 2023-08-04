package cursojava.jpahibernate.orm.modelobanco.repositorios;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cuenta;
import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@Transactional(rollbackFor = NegocioException.class)
public interface RepositorioCuenta {

	void alta(Cuenta cta) throws NegocioException;

	@Transactional(readOnly = true)
	Cuenta buscarPorCodigo(CuentaPk codigo) throws NegocioException;

	void modificar(Cuenta cta) throws NegocioException;

	@Transactional(readOnly = true)
	List<Cuenta> buscarTodas() throws NegocioException;

}
