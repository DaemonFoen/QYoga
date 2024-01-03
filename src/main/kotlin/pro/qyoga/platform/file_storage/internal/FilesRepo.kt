package pro.qyoga.platform.file_storage.internal

import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository
import org.springframework.data.mapping.model.BasicPersistentEntity
import org.springframework.data.util.TypeInformation
import org.springframework.stereotype.Repository
import pro.qyoga.platform.file_storage.api.File

@Repository
class FilesRepo(
    jdbcAggregateTemplate: JdbcAggregateOperations,
    jdbcConverter: JdbcConverter
) : SimpleJdbcRepository<File, Long>(
    jdbcAggregateTemplate,
    BasicPersistentEntity(TypeInformation.of(File::class.java)),
    jdbcConverter
)