package world.tasks

import scala.concurrent.{ ExecutionContext, Future }

/*
 * We can give an async façade to a non async API
 *
 * A good practice consist to use another thread pool
 */
class CustomTaskService(taskRepository: CustomTaskRepository)(implicit ec: ExecutionContext) {

  def list: Future[Iterable[CustomTask]] =
    Future {
      taskRepository.list
    }

  def delete(id: String): Future[Option[CustomTask]] =
    Future { taskRepository.delete(id) }

  def add(): Future[Option[CustomTask]] =
    Future {
      taskRepository.add()
    }

  def get(id: String): Future[Option[CustomTask]] =
    Future {
      taskRepository.get(id)
    }

}
