<%@ page import="estoque.UsuarioGrupo" %>



<div class="fieldcontain ${hasErrors(bean: usuarioGrupoInstance, field: 'grupo', 'error')} required">
	<label for="grupo">
		<g:message code="usuarioGrupo.grupo.label" default="Grupo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="grupo" name="grupo.id" from="${estoque.Grupo.list()}" optionKey="id" required="" value="${usuarioGrupoInstance?.grupo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usuarioGrupoInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="usuarioGrupo.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${estoque.Usuario.list()}" optionKey="id" required="" value="${usuarioGrupoInstance?.usuario?.id}" class="many-to-one"/>
</div>

