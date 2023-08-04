package cursojava.jpahibernate.orm.modelobanco.repositorios.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cursojava.jpahibernate.orm.modelobanco.entidades.Movimiento;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioMovimiento;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@Repository
//@Transactional(rollbackFor = NegocioException.class)
public class RepositorioMovimientoJPABean implements RepositorioMovimiento {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	// @Transactional(rollbackFor = NegocioException.class)
	public void alta(Movimiento mov) throws NegocioException {
		
		try {
			
			em.persist(mov);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo dar alta de movimiento " + mov, e);
		}
		
	}

	@Override
	//@Transactional(readOnly = true)
	public Movimiento buscarPorId(Integer id) throws NegocioException {

		try {
			
			return em.find(Movimiento.class, id);
			
		} catch (Exception e) {

			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar movimiento con id:" + id, e);
		}

	}

	@Override
	// @Transactional(rollbackFor = NegocioException.class)
	public void modificar(Movimiento mov) throws NegocioException {
		
		try {
			
			em.merge(mov);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo modificar el movimiento " + mov, e);
		}
		
	}

	@Override
	//@Transactional(readOnly = true)
	public List<Movimiento> buscarTodos() throws NegocioException {
		
		try {
			
			return em.createNamedQuery("Movimiento.leerTodosLoMovimientos", Movimiento.class).getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar movimientos", e);
		}
	}

}





