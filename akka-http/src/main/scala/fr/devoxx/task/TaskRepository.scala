package fr.devoxx.task

import scala.collection.mutable

trait TaskRepository {
  def list: Iterable[CustomTask]

  def add(task: CustomTask): Option[CustomTask]

  def get(id: String): Option[CustomTask]

  def delete(id: String): Option[CustomTask]
}

class DummyTaskRepository extends TaskRepository {
  private val contents: mutable.Map[String, CustomTask] = scala.collection.mutable.Map.empty

  def list: Iterable[CustomTask] = contents.values

  def add(task: CustomTask): Option[CustomTask] =
    if (get(task.id).isEmpty) {
      contents += (task.id -> task)
      Some(task)
    } else {
      None
    }

  def get(id: String): Option[CustomTask] = contents.get(id)

  def delete(id: String): Option[CustomTask] = {
    val maybeTask = get(id)
    if (maybeTask.isDefined) {
      contents -= id
      maybeTask
    } else {
      None
    }
  }
}