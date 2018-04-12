package world.tasks

import scala.collection.mutable

/*
 * This is a dummy repo only used to show a blocking repo
 */
class CustomTaskRepository {

  private val contents: mutable.Map[String, CustomTask] = scala.collection.mutable.Map.empty

  def delete(id: String): Option[CustomTask] = {
    contents - id
    get(id)
  }

  def list: Iterable[CustomTask] = contents.values

  def add(task: CustomTask): Option[CustomTask] =
    if (get(task.id).isDefined) {
      contents + (task.id -> task)
      Some(task)
    }
    else None

  def get(id: String): Option[CustomTask] = contents.get(id)

}
