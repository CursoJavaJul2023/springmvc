package cursojava.jpahibernate.orm.modelobanco.repositorios.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cuenta;
import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCuenta;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@Repository
//@Transactional(rollbackFor = NegocioException.class)
public class RepositorioCuentaJPABean implements RepositorioCuenta {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	// @Transactional(rollbackFor = NegocioException.class)
	public void alta(Cuenta cta) throws NegocioException {
		
		try {
			
			em.persist(cta);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo dar alta de cuenta " + cta, e);
		}
		
	}

	@Override
	//@Transactional(readOnly = true)
	public Cuenta buscarPorCodigo(CuentaPk codigo) throws NegocioException {

		try {
			
			return em.find(Cuenta.class, codigo);
			
		} catch (Exception e) {

			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar cuenta con código:" + codigo, e);
		}

	}

	@Override
	// @Transactional(rollbackFor = NegocioException.class)
	public void modificar(Cuenta cta) throws NegocioException {
		
		try {
			
			em.merge(cta);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo modificar el cliente " + cta, e);
		}
		
	}

	@Override
	//@Transactional(readOnly = true)
	public List<Cuenta> buscarTodas() throws NegocioException {
		
		try {
			
			return em.createNamedQuery("Cuenta.leerTodasLasCuentas", Cuenta.class).getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar cuentas", e);
		}
	}

}





