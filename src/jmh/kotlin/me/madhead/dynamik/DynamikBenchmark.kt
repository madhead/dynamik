package me.madhead.dynamik

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.madhead.dynamik.types.Order
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.concurrent.TimeUnit

@Fork(3)
@Warmup(iterations = 3)
@State(Scope.Thread)
open class DynamikBenchmark {
    private lateinit var dynamik: Dynamik
    private lateinit var tableSchema: TableSchema<Order>
    private lateinit var item: Order
    private lateinit var map: Map<String, AttributeValue>

    @Setup
    fun setupDynamik() {
        dynamik = Dynamik.Default
        tableSchema = TableSchema.fromBean(Order::class.java)
    }

    @Setup(Level.Iteration)
    fun setupData() {
        item = Json.decodeFromStream<Order>(this.javaClass.classLoader.getResourceAsStream("order.json")!!)
        map = tableSchema.itemToMap(item, false)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun encode(blackhole: Blackhole) {
        TODO()
        // blackhole.consume(dynamik.encode(item))
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun decode(blackhole: Blackhole) {
        TODO()
        // blackhole.consume(dynamik.decode<Order>(map))
    }
}
