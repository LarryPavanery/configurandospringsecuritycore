<%@ page import="estoque.Estoque" %>



<div class="fieldcontain ${hasErrors(bean: estoqueInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="estoque.nome.label" default="Nome" />
		
	</label>
	<g:textField name="nome" value="${estoqueInstance?.nome}"/>
</div>

