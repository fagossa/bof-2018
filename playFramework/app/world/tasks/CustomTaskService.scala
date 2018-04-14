package world.tasks

import akka.NotUsed
import akka.stream.scaladsl.Source

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

  def listStream: Source[CustomTask, NotUsed] =
    Source.fromIterator(() => taskRepository.list.toIterator)

  def delete(id: String): Future[Option[CustomTask]] =
    Future { taskRepository.delete(id) }

  def add(task: CustomTask): Future[Option[CustomTask]] =
    Future {
      taskRepository.add(task)
    }

  def get(id: String): Future[Option[CustomTask]] =
    Future {
      taskRepository.get(id)
    }

}
