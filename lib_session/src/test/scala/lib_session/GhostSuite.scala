package lib_session

import munit.Clue.generate
import munit.FunSuite
import lib_session.Ghost
import lib_session.Storage
import lib_session.Record
import java.util.UUID

val name = "users"
val data = Map("user" -> "Bob")
val defaultId = UUID.randomUUID()
val record: Record[String] = Record(data)
val storage: Storage[String] = Storage(name)

class GhostSuite extends FunSuite:
  test("createRecord creates record with random UUID"):
    assertEquals(record.data, data)
    assert(record.id.isInstanceOf[UUID])

  test("addRecord adds a record in the storage"):
    val obtained = Ghost(storage).add(record).storage
    assertEquals(obtained.name, name)
    assertEquals(
      obtained.records.getOrElse(record.id, defaultId),
      record
    )

  test("clearStorage removes all records"):
    val obtained = Ghost(storage).add(record).clear().storage
    assertEquals(obtained, storage)

  test("clearStorage on empty storage returns empty storage"):
    val obtained = Ghost(storage).clear().storage
    assertEquals(obtained, storage)

  test("addRecord to non-empty storage adds the record"):
    val obtained = Ghost(storage).add(record).add(record).add(record).storage
    val expected = Ghost(storage).add(record).storage
    assertEquals(obtained, expected)

  test("getRecord from empty storage returns None"):
    val obtained = storage.records.get(defaultId)
    assertEquals(obtained, None)

  test("getRecord with existing id returns Some(record)"):
    val obtained =
      Ghost(storage).add(record).storage.records.get(record.id)
    assertEquals(obtained, Some(record))

  test("getRecord with non-existing id returns None"):
    val obtained = Ghost(storage).add(record).storage.records.get(defaultId)
    assertEquals(obtained, None)

  test("getRecord finds record in storage"):
    val obtained =
      Ghost(storage).add(record).storage.records.getOrElse(record.id, defaultId)
    assertEquals(obtained, record)

  // test("removeRecord from empty storage returns unchanged storage"):
  //   val storage = Storage[String]("users", Map.empty)
  //   val obtained = Ghost.removeRecord(UUID.randomUUID())(storage)
  //   assertEquals(obtained, storage)

  // test("removeRecord with non-existing id returns unchanged storage"):
  //   val id1 = UUID.randomUUID()
  //   val record1 = Record(id1, Map("name" -> "Alice"))
  //   val storage = Storage("users", Map(id1 -> record1))
  //   val obtained = Ghost.removeRecord(UUID.randomUUID())(storage)
  //   assertEquals(obtained, storage)

  // test("removeRecord with existing id removes the record"):
  //   val id1 = UUID.randomUUID()
  //   val id2 = UUID.randomUUID()
  //   val record1 = Record(id1, Map("name" -> "Alice"))
  //   val record2 = Record(id2, Map("name" -> "Bob"))
  //   val storage = Storage("users", Map(id1 -> record1, id2 -> record2))
  //   val obtained = Ghost.removeRecord(id1)(storage)
  //   val expected = Storage("users", Map(id2 -> record2))
  //   assertEquals(obtained, expected)

  // // test("removeRecord from single record storage results in empty storage"):
  // //   val id1 = UUID.randomUUID()
  // //   val record1 = Record(id1, Map("name" -> "Alice"))
  // //   val storage = Storage("users", Map(id1 -> record1))
  // //   val obtained = Ghost.removeRecord(id1)(storage)
  // //   val expected = Storage("users", Map.empty)
  // //   assertEquals(obtained, expected)

  // test("removeRecord from storage with multiple records"):
  //   val id1 = UUID.randomUUID()
  //   val id2 = UUID.randomUUID()
  //   val id3 = UUID.randomUUID()
  //   val record1 = Record(id1, Map("name" -> "Alice"))
  //   val record2 = Record(id2, Map("name" -> "Bob"))
  //   val record3 = Record(id3, Map("name" -> "Charlie"))
  //   val storage =
  //     Storage("users", Map(id1 -> record1, id2 -> record2, id3 -> record3))
  //   val obtained = Ghost.removeRecord(id2)(storage)
  //   val expected = Storage("users", Map(id1 -> record1, id3 -> record3))
  //   assertEquals(obtained, expected)
