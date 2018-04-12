# playFramework example in scala

This project is an example intended to show some basic functionality of playFramework.

Some topics include:

## Compile-time dependency injection

```scala

```

## Actions

```scala

```

## Bodyparsers

```scala

```

## play-json

```scala

```

## Integration test

```scala
"send 201 on a POST /task" in {
  val aTask = CustomTask(UUID.randomUUID().toString, "Do something")
  val response = route(app, FakeRequest(POST, "/task", headers, aTask.toJson))
  response.map(status(_)) mustBe Some(CREATED)
}
```

