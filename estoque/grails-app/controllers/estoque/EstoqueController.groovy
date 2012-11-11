package estoque

import org.springframework.dao.DataIntegrityViolationException

class EstoqueController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [estoqueInstanceList: Estoque.list(params), estoqueInstanceTotal: Estoque.count()]
    }

    def create() {
        [estoqueInstance: new Estoque(params)]
    }

    def save() {
        def estoqueInstance = new Estoque(params)
        if (!estoqueInstance.save(flush: true)) {
            render(view: "create", model: [estoqueInstance: estoqueInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoqueInstance.id])
        redirect(action: "show", id: estoqueInstance.id)
    }

    def show(Long id) {
        def estoqueInstance = Estoque.get(id)
        if (!estoqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estoque.label', default: 'Estoque'), id])
            redirect(action: "list")
            return
        }

        [estoqueInstance: estoqueInstance]
    }

    def edit(Long id) {
        def estoqueInstance = Estoque.get(id)
        if (!estoqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estoque.label', default: 'Estoque'), id])
            redirect(action: "list")
            return
        }

        [estoqueInstance: estoqueInstance]
    }

    def update(Long id, Long version) {
        def estoqueInstance = Estoque.get(id)
        if (!estoqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estoque.label', default: 'Estoque'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (estoqueInstance.version > version) {
                estoqueInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'estoque.label', default: 'Estoque')] as Object[],
                          "Another user has updated this Estoque while you were editing")
                render(view: "edit", model: [estoqueInstance: estoqueInstance])
                return
            }
        }

        estoqueInstance.properties = params

        if (!estoqueInstance.save(flush: true)) {
            render(view: "edit", model: [estoqueInstance: estoqueInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoqueInstance.id])
        redirect(action: "show", id: estoqueInstance.id)
    }

    def delete(Long id) {
        def estoqueInstance = Estoque.get(id)
        if (!estoqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estoque.label', default: 'Estoque'), id])
            redirect(action: "list")
            return
        }

        try {
            estoqueInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'estoque.label', default: 'Estoque'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'estoque.label', default: 'Estoque'), id])
            redirect(action: "show", id: id)
        }
    }
}
