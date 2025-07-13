package lib_session

import munit.Clue.generate
import munit.FunSuite
import lib_session.Ghost
import lib_session.GhostStorage
import lib_session.GhostRecord
import java.util.UUID

val name = "users"
val data = Map("user" -> "Bob")
val defaultId = UUID.randomUUID()
val record: GhostRecord[String] = GhostRecord(data)
val storage: GhostStorage[String] = GhostStorage(name)

class GhostSuite extends FunSuite:
  test(
    "GhostRecord.apply should create a record with the provided data and a random UUID"
  ):
    assertEquals(record.data, data)
    assert(record.id.isInstanceOf[UUID])

  test(
    "Ghost.add should add a record to the storage and preserve storage name"
  ):
    val obtained = Ghost(storage).add(record).storage
    assertEquals(obtained.name, name)
    assertEquals(
      obtained.records.getOrElse(record.id, defaultId),
      record
    )

  test("Ghost.clear should remove all records from storage"):
    val obtained = Ghost(storage).add(record).clear().storage
    assertEquals(obtained, storage)

  test("Ghost.clear should return unchanged storage when already empty"):
    val obtained = Ghost(storage).clear().storage
    assertEquals(obtained, storage)

  test(
    "Ghost.add should handle adding the same record multiple times (idempotent)"
  ):
    val obtained = Ghost(storage).add(record).add(record).add(record).storage
    val expected = Ghost(storage).add(record).storage
    assertEquals(obtained, expected)

  test(
    "GhostStorage.records.get should return None when record ID does not exist in empty storage"
  ):
    val obtained = storage.records.get(defaultId)
    assertEquals(obtained, None)

  test(
    "GhostStorage.records.get should return Some(record) when record ID exists"
  ):
    val obtained =
      Ghost(storage).add(record).storage.records.get(record.id)
    assertEquals(obtained, Some(record))

  test(
    "GhostStorage.records.get should return None when record ID does not exist in populated storage"
  ):
    val obtained = Ghost(storage).add(record).storage.records.get(defaultId)
    assertEquals(obtained, None)

  test(
    "GhostStorage.records.getOrElse should return the record when ID exists"
  ):
    val obtained =
      Ghost(storage).add(record).storage.records.getOrElse(record.id, defaultId)
    assertEquals(obtained, record)

  test(
    "Ghost.remove should return unchanged storage when removing from empty storage"
  ):
    val obtained = Ghost(storage).remove(defaultId).storage
    assertEquals(obtained, storage)

  test(
    "Ghost.remove should return unchanged storage when removing non-existing record ID"
  ):
    val obtained = Ghost(storage).add(record).remove(defaultId).storage
    val expected = Ghost(storage).add(record).storage
    assertEquals(obtained, expected)

  test("Ghost.remove should successfully remove an existing record by ID"):
    val obtained = Ghost(storage).add(record).remove(record.id).storage
    assertEquals(obtained, storage)
