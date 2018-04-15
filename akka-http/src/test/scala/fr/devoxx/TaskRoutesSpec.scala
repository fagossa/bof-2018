package fr.devoxx

import java.util.UUID

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import fr.devoxx.task.{CustomTask, DummyTaskRepository, TaskRoutes, TaskService}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

class TaskRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {
  "TaskRoutes" should {

    "return tasks" in {
      //given
      val taskRepository = new DummyTaskRepository()
      val taskService = new TaskService(taskRepository)
      val taskRoutes = new TaskRoutes(taskService)

      val customTask = CustomTask(UUID.randomUUID().toString, UUID.randomUUID().toString)
      taskRepository.add(customTask)
      val request = HttpRequest(uri = "/tasks")

      //when
      request ~> taskRoutes.routes ~> check {
        //then
        status shouldBe StatusCodes.OK

        contentType shouldBe ContentTypes.`application/json`

        entityAs[Seq[CustomTask]] should contain theSameElementsAs Seq(customTask)
      }
    }
  }
}
