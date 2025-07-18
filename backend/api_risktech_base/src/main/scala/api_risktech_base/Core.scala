package akeptous.api.risktech.base

import akeptous.lib.session.Storage
import akeptous.lib.server.http._

object Core extends Api:
  val storage = Storage("Hello from RickTech!")
  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text(storage.name)),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryOrElse[String]("name", "World")
        Response.text(s"Hello $name!")
      }
    )

  def run = Server.serve(routes).provide(Server.defaultWithPort(1234))
