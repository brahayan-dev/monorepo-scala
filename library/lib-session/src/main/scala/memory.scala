package akeptous.lib.session.memory

import java.util.UUID

type Data[V] = Map[String, V]

case class Record[V](id: UUID, data: Data[V])

object Record:
  def apply[V](data: Data[V]): Record[V] =
    Record(UUID.randomUUID(), data)

case class Storage[V](name: String, records: Map[UUID, Record[V]]):
  def clear(): Storage[V] =
    copy(records = Map.empty)

  def add(record: Record[V]): Storage[V] =
    copy(records = records + (record.id -> record))

  def remove(id: UUID): Storage[V] =
    copy(records = records - id)

object Storage:
  def apply[V](name: String): Storage[V] =
    Storage(name, Map.empty)
