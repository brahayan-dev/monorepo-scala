package api_risktech_base

import lib_session.Storage

@main
def run() =
  val storage = Storage("users")
  println(storage)
