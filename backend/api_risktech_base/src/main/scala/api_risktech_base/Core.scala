package akeptous.api.risktech.base

import akeptous.lib.session.Storage

import zio._
import zio.http._

object Core extends ZIOAppDefault:
  val storage = Storage("Hello from Storage!")
  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text(storage.name)),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryOrElse[String]("name", "World")
        Response.text(s"Hello $name!")
      }
    )

  def run = Server.serve(routes).provide(Server.defaultWithPort(1234))
