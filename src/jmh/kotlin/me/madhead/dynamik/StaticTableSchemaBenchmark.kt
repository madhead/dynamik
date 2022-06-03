package me.madhead.dynamik

import me.madhead.dynamik.types.Order
import me.madhead.dynamik.types.OrderLineItem
import me.madhead.dynamik.types.Status
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
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTag
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.math.BigDecimal
import java.time.Instant
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

@Fork(3)
@Warmup(iterations = 3)
@State(Scope.Thread)
open class StaticTableSchemaBenchmark {
    private lateinit var tableSchema: TableSchema<Order>
    private lateinit var item: Order
    private lateinit var map: Map<String, AttributeValue>

    @Setup
    fun setupTableSchema() {
        val orderLineItemTableSchema = TableSchema
            .builder(OrderLineItem::class.java)
            .newItemSupplier(::OrderLineItem)
            .attribute("id", Long::class, OrderLineItem::id)
            .attribute("quantity", Int::class, OrderLineItem::quantity)
            .attribute("price", BigDecimal::class, OrderLineItem::price)
            .attribute("currency", String::class, OrderLineItem::currency)
            .attribute("displayName", String::class, OrderLineItem::displayName)
            .attribute(
                "displayNameTranslations",
                EnhancedType.mapOf(String::class.java, String::class.java),
                OrderLineItem::displayNameTranslations
            )
            .build()

        tableSchema = TableSchema
            .builder(Order::class.java)
            .newItemSupplier(::Order)
            .attribute("id", Long::class, Order::id, StaticAttributeTags.primaryPartitionKey())
            .attribute("status", Status::class, Order::status)
            .attribute("price", BigDecimal::class, Order::totalPrice)
            .attribute("currency", String::class, Order::currency)
            .attribute("bonusCredits", Int::class, Order::bonusCredits)
            .attribute("vat", Boolean::class, Order::vat)
            .attribute("date", Instant::class, Order::date)
            .attribute(
                "items",
                EnhancedType.listOf(EnhancedType.documentOf(OrderLineItem::class.java, orderLineItemTableSchema)),
                Order::items
            )
            .attribute(
                "tags",
                EnhancedType.listOf(String::class.java),
                Order::tags
            )
            .build()
    }

    @Setup(Level.Iteration)
    fun setupData() {
        item = Order.random
        map = tableSchema.itemToMap(item, false)
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

    private fun <T, A : Any> StaticTableSchema.Builder<T>.attribute(
        name: String,
        attributeClass: KClass<A>,
        property: KMutableProperty1<T, A>,
        vararg tags: StaticAttributeTag,
    ): StaticTableSchema.Builder<T> {
        this.addAttribute(attributeClass.java) {
            it
                .name(name)
                .getter(property::get)
                .setter(property::set)

            if (tags.isNotEmpty()) {
                it.tags(*tags)
            }
        }

        return this
    }

    private fun <T, A> StaticTableSchema.Builder<T>.attribute(
        name: String,
        attributeEnhancedType: EnhancedType<A>,
        property: KMutableProperty1<T, A>,
    ): StaticTableSchema.Builder<T> {
        this.addAttribute(attributeEnhancedType) {
            it
                .name(name)
                .getter(property::get)
                .setter(property::set)
        }

        return this
    }
}
