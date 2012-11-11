import estoque.*

class BootStrap {
    def init = { servletContext ->
		def adminGrupo = Grupo.findByAuthority('ROLE_ADMIN') ?: new Grupo(authority: 'ROLE_ADMIN').save(failOnError: true)
		def adminUsuario= Usuario.findByUsername('admin') ?: new Usuario(
			username: 'admin',
			password: 'admin',
			enabled: true).save(failOnError:true)

		if(!adminUsuario.authorities.contains(adminGrupo)){
			UsuarioGrupo.create adminUsuario, adminGrupo
		}
    }
    def destroy = {
    }
}
