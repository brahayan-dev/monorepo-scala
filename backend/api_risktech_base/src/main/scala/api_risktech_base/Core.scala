package api_risktech_base

import lib_session.Storage
import lib_server.Http

@main
def run() =
  val storage = Storage("users")
  println(storage)
  Http.run
