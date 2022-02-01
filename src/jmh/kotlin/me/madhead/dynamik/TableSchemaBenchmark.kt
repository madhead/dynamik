package me.madhead.dynamik

import me.madhead.dynamik.types.Customer
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.Instant
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Fork(3)
@Warmup(iterations = 3)
@State(Scope.Thread)
open class TableSchemaBenchmark {
    private lateinit var tableSchema: TableSchema<Customer>
    private lateinit var item: Customer
    private lateinit var map: Map<String, AttributeValue>

    @Setup
    fun setupTableSchema() {
        tableSchema = TableSchema.fromBean(Customer::class.java)
    }

    @Setup(Level.Iteration)
    fun setupData() {
        item = Customer(
            id = Random.nextLong(),
            name = List(10) { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }.joinToString(""),
            registrationDate = Instant.now(),
        )
        map = mapOf(
            "id" to AttributeValue.builder().n(item.id.toString()).build(),
            "name" to AttributeValue.builder().s(item.name).build(),
            "registrationDate" to AttributeValue.builder().s(item.registrationDate.toString()).build(),
        )
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun itemToMap(blackhole: Blackhole) {
        blackhole.consume(tableSchema.itemToMap(item, true))
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun mapToItem(blackhole: Blackhole) {
        blackhole.consume(tableSchema.mapToItem(map))
    }
}
