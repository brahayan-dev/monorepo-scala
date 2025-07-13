package lib_session

import java.util.UUID

case class Record[V](id: UUID, data: Map[String, V])
object Record:
  def apply[V](data: Map[String, V]): Record[V] =
    Record(UUID.randomUUID(), data)

case class Storage[V](name: String, records: Map[UUID, Record[V]])
object Storage:
  def apply[V](name: String): Storage[V] =
    Storage(name, Map.empty)

case class Ghost[V](storage: Storage[V]):
  def clear(): Ghost[V] =
    Ghost(storage.copy(records = Map.empty))

  def add(record: Record[V]): Ghost[V] =
    val Storage(_, records) = storage
    val updatedStorage = storage.copy(records = records + (record.id -> record))
    Ghost(updatedStorage)

  def remove(id: UUID): Ghost[V] =
    val Storage(_, records) = storage
    if records.isEmpty then Ghost(storage)
    else Ghost(storage.copy(records = records - id))
