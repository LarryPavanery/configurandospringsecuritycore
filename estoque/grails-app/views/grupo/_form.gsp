<%@ page import="estoque.Grupo" %>



<div class="fieldcontain ${hasErrors(bean: grupoInstance, field: 'authority', 'error')} required">
	<label for="authority">
		<g:message code="grupo.authority.label" default="Authority" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="authority" required="" value="${grupoInstance?.authority}"/>
</div>

