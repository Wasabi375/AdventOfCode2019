package day3.star5

import day3.*
import kotlin.math.abs

private class World {

	private val state: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

	private var nextLineId = 0

	val crossings = mutableSetOf<Vec2>()

	private fun getColumn(x: Int): MutableMap<Int, Int> {
		return if(state.containsKey(x)) state[x]!!
		else {
			val column = mutableMapOf<Int, Int>()
			state[x] = column
			column
		}
	}

	operator fun get(pos: Vec2) = getColumn(pos.x)[pos.y] ?: 0

	private fun set(pos: Vec2, v: Int) {
		getColumn(pos.x)[pos.y] = v
	}

	fun addPoint(pos: Vec2, line: Int) {
		val old = get(pos)
		val lineBit = 1 shl line
		val new = old or lineBit

		set(pos, new)

		if((old and lineBit.inv()) != 0){
			crossings.add(pos)
		}
	}

	fun addLine(commands: Sequence<MoveCommand>) {
		val lineId = nextLineId++
		var current = Vec2(0, 0)
		for(c in commands) {
			for(p in c.run(current)) {
				current = p
				addPoint(p, lineId)
			}
		}
	}
}

data class Vec2(val x: Int, val y: Int){
	operator fun plus(o: Vec2): Vec2 {
		return Vec2(x + o.x, y + o.y)
	}
}

private enum class Direction(val dir: Vec2){
	U(Vec2(0, 1)),
	L(Vec2(-1, 0)),
	R(Vec2(1, 0)),
	D(Vec2(0, -1));

	companion object{
		fun from(c: Char): Direction = when(c.toUpperCase()) {
			'U' -> U
			'L' -> L
			'R' -> R
			'D' -> D
			else -> throw IllegalArgumentException()
		}
	}
}

private class MoveCommand(val dir: Direction, val dist: Int) {

	fun run(start: Vec2): Sequence<Vec2> {
		return sequence {
			var current = start
			repeat(dist) {
				current += dir.dir
				yield(current)
			}
		}
	}

	companion object {
		fun from(s: String): MoveCommand {
			val dir = Direction.from(s[0])
			val dist = s.drop(1).toInt()
			return MoveCommand(dir, dist)
		}
	}
}

fun distance(a: Vec2, b: Vec2 = Vec2(0, 0)): Int = abs(b.x - a.x + b.y - a.y)

private fun String.toCommands() = splitToSequence(",").map { it.trim() }.map { MoveCommand.from(it) }

fun star5() {
	println("day 3 star 5")

	val world = World()
	world.addLine(line1Input.toCommands())
	world.addLine(line2Input.toCommands())

	val result = world.crossings.filter { world[it] == 3 }.minBy { distance(it) }!!
	println("result position $result with distance ${distance(result)}")
}