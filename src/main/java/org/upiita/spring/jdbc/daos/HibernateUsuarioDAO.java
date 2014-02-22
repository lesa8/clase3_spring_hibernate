package org.upiita.spring.jdbc.daos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.upiita.spring.jdbc.entidades.Usuario;


@Component("usuarioDAO")
public class HibernateUsuarioDAO implements UsuarioDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Usuario buscaUsuarioPorId(Integer usuarioId) {
		//pide la session, la crea
		Session sesion = sessionFactory.openSession();
		
		//inicializa la transaccion
		sesion.beginTransaction();
		
		//.class tiene asociada y se puede accer por medio de una instancia y acceder a sus metodos y variables  
		//castea (convierte) a un objeto de tipo Usuario que es laclase del objeto que se desea mapear con hibernate
		Usuario usuario = (Usuario) sesion.get(Usuario.class, usuarioId);
				
		//realiza las afectaciones en la BD
		//obtiene la transaccion y reliza los cambios en BD
		sesion.getTransaction().commit();
		//cierra la sesion
		sesion.close();

		return usuario;
	}

	public void creaUsuario(Usuario usuario) {
		//pide la session, la crea
		Session sesion = sessionFactory.openSession();
		
		//inicializa la transaccion
		sesion.beginTransaction();
		
		//inserta registros
		//sesion.save(usuario);
		//Inserta registros, si ya existe el registro por su id realizar update
		sesion.saveOrUpdate(usuario);
		
		//realiza las afectaciones en la BD
		//obtiene la transaccion y reliza los cambios en BD
		sesion.getTransaction().commit();
		//cierra la sesion
		sesion.close();

	}

	//Se eutilizan los criterios de hibernate para realizar consultas mas refinadas
	public Usuario buscaPoeEmailPassword(String email, String password) {
		Session sesion = sessionFactory.openSession();
		
		//inicializa la transaccion
		sesion.beginTransaction();
				
		//FORMAMOS CRITERIO DE HIBERNATE
		//regresa un objeto de tipo de datos criteria
		Criteria criterio = sesion.createCriteria(Usuario.class);
		
		//"email"=nombre de la propiedad de tu clase
		//ejemplo que por default usa la operacion and
		/*
		criterio.add(Restrictions.and(Restrictions.eq("email", email),
									Restrictions.eq("password", password)));
		*/
		
		criterio.add(Restrictions.eq("email", email));
		criterio.add(Restrictions.eq("password", password));
		
		//devuelve un objeto que va a ser del tipo Usuario
		//Obtiene un solo resultado, si no encuentra nada regresa null; solo se utiliza cuando es un solo result sino marca excepcion
		Usuario usuario = (Usuario) criterio.uniqueResult();
		
		sesion.getTransaction().commit();
		sesion.close();
		return usuario;
	}

	public List<Usuario> buscaPorNombre(String nombre) {
	Session sesion = sessionFactory.openSession();
		//inicializa la transaccion
		sesion.beginTransaction();
				
		//FORMAMOS CRITERIO DE HIBERNATE
		Criteria criterio = sesion.createCriteria(Usuario.class);
		
		criterio.add(Restrictions.like("nombre","%" +nombre+ "%"));

		List<Usuario> usuarios = criterio.list();
		
		sesion.getTransaction().commit();
		sesion.close();
		
		return usuarios;
	}
	

}
