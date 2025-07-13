package lib_session

import munit.FunSuite
import lib_session.Ghost
import lib_session.Storage

class GhostSuite extends FunSuite:
  test("createStorage creates an empty storage"):
    val obtained = Ghost.createStorage("users")
    val expected = Storage("users", Vector.empty)
    assertEquals(obtained, expected)

  // test("clearStorage removes all records"):
  //   val storage = Storage(
  //     "users",
  //     Vector(Record(1, Map("name" -> "Alice")), Record(2, Map("name" -> "Bob")))
  //   )
  //   val obtained = Ghost.clearStorage(storage)
  //   val expected = Storage("users", Vector.empty)
  //   assertEquals(obtained, expected)

  test("clearStorage on empty storage returns empty storage"):
    val storage = Storage[String]("users", Vector.empty)
    val obtained = Ghost.clearStorage(storage)
    assertEquals(obtained, storage)

  test("createRecord creates record with id 0"):
    val data = Map("name" -> "Alice", "age" -> "30")
    val obtained = Ghost.createRecord(data)
    val expected = Record(0, data)
    assertEquals(obtained, expected)

  test("addRecord to empty storage assigns id 1"):
    val storage = Storage[String]("users", Vector.empty)
    val record = Record(0, Map("name" -> "Alice"))
    val obtained = Ghost.addRecord(record)(storage)
    val expected = Storage("users", Vector(Record(1, Map("name" -> "Alice"))))
    assertEquals(obtained, expected)

  test("addRecord to non-empty storage assigns incremental id"):
    val storage = Storage(
      "users",
      Vector(Record(1, Map("name" -> "Alice")), Record(2, Map("name" -> "Bob")))
    )
    val record = Record(0, Map("name" -> "Charlie"))
    val obtained = Ghost.addRecord(record)(storage)
    val expected = Storage(
      "users",
      Vector(
        Record(1, Map("name" -> "Alice")),
        Record(2, Map("name" -> "Bob")),
        Record(3, Map("name" -> "Charlie"))
      )
    )
    assertEquals(obtained, expected)

  test("getRecord from empty storage returns None"):
    val storage = Storage[String]("users", Vector.empty)
    val obtained = Ghost.getRecord(1)(storage)
    assertEquals(obtained, None)

  test("getRecord with existing id returns Some(record)"):
    val record1 = Record(1, Map("name" -> "Alice"))
    val record2 = Record(2, Map("name" -> "Bob"))
    val storage = Storage("users", Vector(record1, record2))
    val obtained = Ghost.getRecord(1)(storage)
    assertEquals(obtained, Some(record1))

  // test("getRecord with non-existing id returns None"):
  //   val storage = Storage("users", Vector(Record(1, Map("name" -> "Alice"))))
  //   val obtained = Ghost.getRecord(99)(storage)
  //   assertEquals(obtained, None)

  test("getRecord finds record in middle of storage"):
    val record1 = Record(1, Map("name" -> "Alice"))
    val record2 = Record(2, Map("name" -> "Bob"))
    val record3 = Record(3, Map("name" -> "Charlie"))
    val storage = Storage("users", Vector(record1, record2, record3))
    val obtained = Ghost.getRecord(2)(storage)
    assertEquals(obtained, Some(record2))

  test("removeRecord from empty storage returns unchanged storage"):
    val storage = Storage[String]("users", Vector.empty)
    val obtained = Ghost.removeRecord(1)(storage)
    assertEquals(obtained, storage)

  // test("removeRecord with non-existing id returns unchanged storage"):
  //   val storage = Storage("users", Vector(Record(1, Map("name" -> "Alice"))))
  //   val obtained = Ghost.removeRecord(99)(storage)
  //   assertEquals(obtained, storage)

  test("removeRecord with existing id removes the record"):
    val record1 = Record(1, Map("name" -> "Alice"))
    val record2 = Record(2, Map("name" -> "Bob"))
    val storage = Storage("users", Vector(record1, record2))
    val obtained = Ghost.removeRecord(1)(storage)
    val expected = Storage("users", Vector(record2))
    assertEquals(obtained, expected)

  // test("removeRecord from single record storage results in empty storage"):
  //   val storage = Storage("users", Vector(Record(1, Map("name" -> "Alice"))))
  //   val obtained = Ghost.removeRecord(1)(storage)
  //   val expected = Storage("users", Vector.empty)
  //   assertEquals(obtained, expected)

  test("removeRecord from middle of storage"):
    val record1 = Record(1, Map("name" -> "Alice"))
    val record2 = Record(2, Map("name" -> "Bob"))
    val record3 = Record(3, Map("name" -> "Charlie"))
    val storage = Storage("users", Vector(record1, record2, record3))
    val obtained = Ghost.removeRecord(2)(storage)
    val expected = Storage("users", Vector(record1, record3))
    assertEquals(obtained, expected)
