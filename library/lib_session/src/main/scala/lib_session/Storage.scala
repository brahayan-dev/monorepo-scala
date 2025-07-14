package lib_session

import java.util.UUID

case class StorageRecord[V](id: UUID, data: Map[String, V])

object StorageRecord:
  def apply[V](data: Map[String, V]): StorageRecord[V] =
    StorageRecord(UUID.randomUUID(), data)

case class Storage[V](name: String, records: Map[UUID, StorageRecord[V]]):
  def clear(): Storage[V] =
    copy(records = Map.empty)

  def add(record: StorageRecord[V]): Storage[V] =
    copy(records = records + (record.id -> record))

  def remove(id: UUID): Storage[V] =
    copy(records = records - id)

object Storage:
  def apply[V](name: String): Storage[V] =
    Storage(name, Map.empty)
