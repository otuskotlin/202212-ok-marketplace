package ru.otus.otuskotlin.marketplace.dsl

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.otus.otuskotlin.marketplace.dsl.SqlDsl.query
import kotlin.test.assertFailsWith

// Реализуйте dsl для составления sql запроса, чтобы все тесты стали зелеными.
interface SqlCondition {
    fun build(): String
}

class SqlEqStringCondition(val column: String, val value: String?) : SqlCondition {
    override fun build(): String {
        return if (value != null) {
            "$column = '$value'"
        } else {
            "$column is null"
        }
    }
}

class SqlEqNumberCondition(val column: String, val value: Number) : SqlCondition {
    override fun build(): String {
        return "$column = $value"
    }
}


class SqlNonEqStringCondition(val column: String, val value: String?) : SqlCondition {
    override fun build(): String {
        return if (value != null) {
            "$column <> '$value'"
        } else {
            "$column !is null"
        }
    }
}

class SqlNonEqNumberCondition(val column: String, val value: Number) : SqlCondition {
    override fun build(): String {
        return "$column != $value"
    }
}

class SqlOrConditionExpressionBuilder : SqlListConditionExpressionBuilder() {
    override fun build(): String {
        return if (sqlConditions.size <= 1) {
            sqlConditions.firstOrNull()?.build() ?: ""
        } else sqlConditions.map { it.build() }.joinToString(
            separator = " or ",
            prefix = "(",
            postfix = ")"
        )
    }
}
class SqlAndConditionExpressionBuilder : SqlListConditionExpressionBuilder() {
    override fun build(): String {
        return if (sqlConditions.size <= 1) {
            sqlConditions.firstOrNull()?.build() ?: ""
        } else sqlConditions.map { it.build() }.joinToString(
            separator = " and ",
            prefix = "(",
            postfix = ")"
        )
    }
}

abstract class SqlListConditionExpressionBuilder : SqlCondition {
    val sqlConditions = mutableListOf<SqlCondition>()
    infix fun String.eq(value: String?): SqlCondition {
        return SqlEqStringCondition(this, value).also { sqlConditions.add(it) }
    }

    infix fun String.eq(value: Number): SqlCondition {
        return SqlEqNumberCondition(this, value).also { sqlConditions.add(it) }
    }

    infix fun String.nonEq(value: String?): SqlCondition {
        return SqlNonEqStringCondition(this, value).also { sqlConditions.add(it) }
    }

    infix fun String.nonEq(value: Number): SqlCondition {
        return SqlNonEqNumberCondition(this, value).also { sqlConditions.add(it) }
    }

    fun or(block: SqlOrConditionExpressionBuilder.() -> Unit): SqlCondition {
        return SqlOrConditionExpressionBuilder().apply { block.invoke(this) }.also { sqlConditions.add(it) }
    }

    fun and(block: SqlAndConditionExpressionBuilder.() -> Unit): SqlCondition {
        return SqlAndConditionExpressionBuilder().apply { block.invoke(this) }.also { sqlConditions.add(it) }
    }
}

class SqlWhereBuilder : SqlListConditionExpressionBuilder() {



    //where contains only one condition
    override fun build(): String {
        require(sqlConditions.size <= 1) { "Count of 'where' conditions must be one or zero, but there are ${sqlConditions.size} conditions" }
        return sqlConditions.takeIf { it.size == 1 }?.let { " where " + it.first().build() } ?: ""
    }
}

class SqlSelectBuilder {
    var table: String? = null
    var col = mutableListOf<String>()
    val sqlWhereBuilder = SqlWhereBuilder()
    fun from(table: String) {
        this.table = table
    }

    fun select(vararg col: String) {
        this.col.addAll(col)
    }

    fun where(block: SqlWhereBuilder.() -> Unit) {
        sqlWhereBuilder.apply(block)
    }

    fun build(): String {
        return "select ${columnBlock()} from ${tableBlock()}" + whereBlock()
    }

    private fun tableBlock(): String {
        requireNotNull(table) { "table must be set" }
        return table!!
    }

    private fun columnBlock() = col.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "*"
    private fun whereBlock() = sqlWhereBuilder.build()
}

object SqlDsl {
    fun query(block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
        return SqlSelectBuilder().apply(block)
    }
}

class SqlDslUnitTest {
    private fun checkSQL(expected: String, sql: SqlSelectBuilder) {
        assertEquals(expected, sql.build())
    }

    @Test
    fun `simple select all from table`() {
        val expected = "select * from table"

        val real = query {
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `check that select can't be used without table`() {
        assertFailsWith<Exception> {
            query {
                select("col_a")
            }.build()
        }
    }

    @Test
    fun `select certain columns from table`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select certain columns from table 1`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    /**
     * __eq__ is "equals" function. Must be one of char:
     *  - for strings - "="
     *  - for numbers - "="
     *  - for null - "is"
     */
    @Test
    fun `select with complex where condition with one condition`() {
        val expected = "select * from table where col_a = 'id'"

        val real = query {
            from("table")
            where { "col_a" eq "id" }
        }

        checkSQL(expected, real)
    }

    /**
     * __nonEq__ is "non equals" function. Must be one of chars:
     *  - for strings - "!="
     *  - for numbers - "!="
     *  - for null - "!is"
     */
    @Test
    fun `select with complex where condition with two conditions`() {
        val expected = "select * from table where col_a != 0"

        val real = query {
            from("table")
            where {
                "col_a" nonEq 0
            }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `when 'or' conditions are specified then they are respected`() {
        val expected = "select * from table where (col_a = 4 or col_b !is null)"

        val real = query {
            from("table")
            where {
                or {
                    "col_a" eq 4
                    "col_b" nonEq null
                }
            }
        }

        checkSQL(expected, real)
    }
    @Test
    fun `when 'and' conditions are specified then they are respected`() {
        val expected = "select * from table where (col_a = 4 and col_b !is null)"

        val real = query {
            from("table")
            where {
                and {
                    "col_a" eq 4
                    "col_b" nonEq null
                }
            }
        }

        checkSQL(expected, real)
    }
    @Test
    fun `when or and and conditions are specified then they are respected`() {
        val expected = "select * from table where (col_a = 4 and col_b !is null and (col_c = 50 or col_d is null) and (col_e = 'val_e' and col_f <> 'val_f'))"

        val real = query {
            from("table")
            where {
                and {
                    "col_a" eq 4
                    "col_b" nonEq null
                    or {
                        "col_c" eq 50
                        "col_d" eq null
                    }
                    and {
                        "col_e" eq "val_e"
                        "col_f" nonEq "val_f"
                    }
                }
            }
        }

        checkSQL(expected, real)
    }
}
