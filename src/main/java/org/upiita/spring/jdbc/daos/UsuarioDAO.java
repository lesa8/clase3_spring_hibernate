package org.upiita.spring.jdbc.daos;

import java.util.List;

import org.upiita.spring.jdbc.entidades.Usuario;

//Comentario Para probar en el repositorio git

public interface UsuarioDAO {

	public Usuario buscaUsuarioPorId(Integer usuarioId);

	public void creaUsuario(Usuario usuario);
	
	//es lo mismo que le pongo o no el public ya que las interfaces son publicas y se desea que se pueda acceder a ellas
	Usuario buscaPoeEmailPassword(String email, String password);
	
	//Generic List<Usuario>
	List<Usuario> buscaPorNombre(String nombre);
}
 