package me.madhead.dynamik

import me.madhead.dynamik.types.Order
import me.madhead.dynamik.types.random
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
        item = Order.random
        map = tableSchema.itemToMap(item, false)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun encode(blackhole: Blackhole) {
        blackhole.consume(dynamik.encode(item))
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun decode(blackhole: Blackhole) {
        blackhole.consume(dynamik.decode<Order>(map))
    }
}
