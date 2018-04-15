package fr.devoxx.task

import akka.NotUsed
import akka.stream.scaladsl.Source

import scala.concurrent.{ExecutionContext, Future}

class TaskService(taskRepository: TaskRepository)(implicit ec: ExecutionContext) {
  def list: Future[Iterable[CustomTask]] =
    Future {
      taskRepository.list
    }

  def listStream: Source[CustomTask, NotUsed] =
    Source.fromIterator(() => taskRepository.list.toIterator)

  def delete(id: String): Future[Option[CustomTask]] =
    Future {
      taskRepository.delete(id)
    }

  def add(task: CustomTask): Future[Option[CustomTask]] =
    Future {
      taskRepository.add(task)
    }

  def get(id: String): Future[Option[CustomTask]] =
    Future {
      taskRepository.get(id)
    }
}
