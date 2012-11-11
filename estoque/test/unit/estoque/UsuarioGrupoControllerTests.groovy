package estoque



import org.junit.*
import grails.test.mixin.*

@TestFor(UsuarioGrupoController)
@Mock(UsuarioGrupo)
class UsuarioGrupoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/usuarioGrupo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.usuarioGrupoInstanceList.size() == 0
        assert model.usuarioGrupoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.usuarioGrupoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.usuarioGrupoInstance != null
        assert view == '/usuarioGrupo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/usuarioGrupo/show/1'
        assert controller.flash.message != null
        assert UsuarioGrupo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/usuarioGrupo/list'

        populateValidParams(params)
        def usuarioGrupo = new UsuarioGrupo(params)

        assert usuarioGrupo.save() != null

        params.id = usuarioGrupo.id

        def model = controller.show()

        assert model.usuarioGrupoInstance == usuarioGrupo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/usuarioGrupo/list'

        populateValidParams(params)
        def usuarioGrupo = new UsuarioGrupo(params)

        assert usuarioGrupo.save() != null

        params.id = usuarioGrupo.id

        def model = controller.edit()

        assert model.usuarioGrupoInstance == usuarioGrupo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/usuarioGrupo/list'

        response.reset()

        populateValidParams(params)
        def usuarioGrupo = new UsuarioGrupo(params)

        assert usuarioGrupo.save() != null

        // test invalid parameters in update
        params.id = usuarioGrupo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/usuarioGrupo/edit"
        assert model.usuarioGrupoInstance != null

        usuarioGrupo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/usuarioGrupo/show/$usuarioGrupo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        usuarioGrupo.clearErrors()

        populateValidParams(params)
        params.id = usuarioGrupo.id
        params.version = -1
        controller.update()

        assert view == "/usuarioGrupo/edit"
        assert model.usuarioGrupoInstance != null
        assert model.usuarioGrupoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/usuarioGrupo/list'

        response.reset()

        populateValidParams(params)
        def usuarioGrupo = new UsuarioGrupo(params)

        assert usuarioGrupo.save() != null
        assert UsuarioGrupo.count() == 1

        params.id = usuarioGrupo.id

        controller.delete()

        assert UsuarioGrupo.count() == 0
        assert UsuarioGrupo.get(usuarioGrupo.id) == null
        assert response.redirectedUrl == '/usuarioGrupo/list'
    }
}
