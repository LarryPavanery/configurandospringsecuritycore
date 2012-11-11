package estoque



import org.junit.*
import grails.test.mixin.*

@TestFor(EstoqueController)
@Mock(Estoque)
class EstoqueControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/estoque/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.estoqueInstanceList.size() == 0
        assert model.estoqueInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.estoqueInstance != null
    }

    void testSave() {
        controller.save()

        assert model.estoqueInstance != null
        assert view == '/estoque/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/estoque/show/1'
        assert controller.flash.message != null
        assert Estoque.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/estoque/list'

        populateValidParams(params)
        def estoque = new Estoque(params)

        assert estoque.save() != null

        params.id = estoque.id

        def model = controller.show()

        assert model.estoqueInstance == estoque
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/estoque/list'

        populateValidParams(params)
        def estoque = new Estoque(params)

        assert estoque.save() != null

        params.id = estoque.id

        def model = controller.edit()

        assert model.estoqueInstance == estoque
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/estoque/list'

        response.reset()

        populateValidParams(params)
        def estoque = new Estoque(params)

        assert estoque.save() != null

        // test invalid parameters in update
        params.id = estoque.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/estoque/edit"
        assert model.estoqueInstance != null

        estoque.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/estoque/show/$estoque.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        estoque.clearErrors()

        populateValidParams(params)
        params.id = estoque.id
        params.version = -1
        controller.update()

        assert view == "/estoque/edit"
        assert model.estoqueInstance != null
        assert model.estoqueInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/estoque/list'

        response.reset()

        populateValidParams(params)
        def estoque = new Estoque(params)

        assert estoque.save() != null
        assert Estoque.count() == 1

        params.id = estoque.id

        controller.delete()

        assert Estoque.count() == 0
        assert Estoque.get(estoque.id) == null
        assert response.redirectedUrl == '/estoque/list'
    }
}
