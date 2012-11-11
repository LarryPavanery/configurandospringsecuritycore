package estoque

import org.springframework.dao.DataIntegrityViolationException

class UsuarioGrupoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [usuarioGrupoInstanceList: UsuarioGrupo.list(params), usuarioGrupoInstanceTotal: UsuarioGrupo.count()]
    }

    def create() {
        [usuarioGrupoInstance: new UsuarioGrupo(params)]
    }

    def save() {
        def usuarioGrupoInstance = new UsuarioGrupo(params)
        if (!usuarioGrupoInstance.save(flush: true)) {
            render(view: "create", model: [usuarioGrupoInstance: usuarioGrupoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), usuarioGrupoInstance.id])
        redirect(action: "show", id: usuarioGrupoInstance.id)
    }

    def show(Long id) {
        def usuarioGrupoInstance = UsuarioGrupo.get(id)
        if (!usuarioGrupoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), id])
            redirect(action: "list")
            return
        }

        [usuarioGrupoInstance: usuarioGrupoInstance]
    }

    def edit(Long id) {
        def usuarioGrupoInstance = UsuarioGrupo.get(id)
        if (!usuarioGrupoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), id])
            redirect(action: "list")
            return
        }

        [usuarioGrupoInstance: usuarioGrupoInstance]
    }

    def update(Long id, Long version) {
        def usuarioGrupoInstance = UsuarioGrupo.get(id)
        if (!usuarioGrupoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (usuarioGrupoInstance.version > version) {
                usuarioGrupoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo')] as Object[],
                          "Another user has updated this UsuarioGrupo while you were editing")
                render(view: "edit", model: [usuarioGrupoInstance: usuarioGrupoInstance])
                return
            }
        }

        usuarioGrupoInstance.properties = params

        if (!usuarioGrupoInstance.save(flush: true)) {
            render(view: "edit", model: [usuarioGrupoInstance: usuarioGrupoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), usuarioGrupoInstance.id])
        redirect(action: "show", id: usuarioGrupoInstance.id)
    }

    def delete(Long id) {
        def usuarioGrupoInstance = UsuarioGrupo.get(id)
        if (!usuarioGrupoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), id])
            redirect(action: "list")
            return
        }

        try {
            usuarioGrupoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), id])
            redirect(action: "show", id: id)
        }
    }
}
