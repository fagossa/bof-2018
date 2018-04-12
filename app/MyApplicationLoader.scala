import java.util.concurrent.Executors

import play.api._
import play.api.routing.Router
import world.tasks.{ CustomTaskController, CustomTaskRepository, CustomTaskService }

import scala.concurrent.ExecutionContext

class MyApplicationLoader extends ApplicationLoader {
  private var components: MyComponents = _

  def load(context: ApplicationLoader.Context): Application = {
    components = new MyComponents(context)
    components.application
  }
}

class MyComponents(context: ApplicationLoader.Context)
    extends BuiltInComponentsFromContext(context)
    with play.filters.HttpFiltersComponents
    with _root_.controllers.AssetsComponents {

  // This could also be done using akka dispatchers!
  implicit val ec: ExecutionContext =
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  lazy val taskRepository = new CustomTaskRepository

  lazy val taskService = new CustomTaskService(taskRepository)

  // We specify the default thread pool using akka dispatchers
  lazy val taskController =
    new CustomTaskController(taskService, controllerComponents)(actorSystem.dispatcher)

  lazy val router: Router = new _root_.router.Routes(httpErrorHandler, taskController, assets)
}
