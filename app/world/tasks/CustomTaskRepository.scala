package world.tasks

import scala.collection.mutable

class CustomTaskRepository {

  private val contents: mutable.Map[String, CustomTask] =
    scala.collection.mutable.Map.empty

  def delete(id: String): Option[CustomTask] = {
    contents - id
    get(id)
  }

  def list: Iterable[CustomTask] = contents.values

  // TODO: complete this function
  def add(): Option[CustomTask] = None

  def get(id: String): Option[CustomTask] = contents.get(id)

}
