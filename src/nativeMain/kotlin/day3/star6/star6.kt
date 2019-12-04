package day3.star6

import day3.*
import kotlin.math.abs

private class World {

	data class WorldEntry(val pos: Vec2, val line1Length: Int, val line2Length: Int)

	private val state: MutableMap<Int, MutableMap<Int, WorldEntry>> = mutableMapOf()

	private var nextLineId = 0

	val crossings = mutableSetOf<Vec2>()

	private fun getColumn(x: Int): MutableMap<Int, WorldEntry> {
		return if(state.containsKey(x)) state[x]!!
		else {
			val column = mutableMapOf<Int, WorldEntry>()
			state[x] = column
			column
		}
	}

	operator fun get(pos: Vec2) = getColumn(pos.x)[pos.y] ?: WorldEntry(pos, -1, -1)

	private fun set(pos: Vec2, v: WorldEntry) {
		getColumn(pos.x)[pos.y] = v
	}

	fun addPoint(pos: Vec2, line: Int, length: Int) {
		require(line in 0..1)
		val old = get(pos)
		assert(old.pos == pos)

		val new = if(line == 0) {
			old.copy(line1Length = length)
		} else {
			old.copy(line2Length = length)
		}

		set(pos, new)
		if(new.line1Length >= 0 && new.line2Length >= 0) {
			crossings.add(pos)
		}
	}

	fun addLine(commands: Sequence<MoveCommand>) {
		val lineId = nextLineId++
		var current = Vec2(0, 0)
		var length = 0
		for(c in commands) {
			for(p in c.run(current)) {
				current = p
				length++
				addPoint(p, lineId, length)
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
		fun from(c: Char):  Direction = when(c.toUpperCase()) {
			'U' -> U
			'L' -> L
			'R' -> R
			'D' -> D
			else -> throw IllegalArgumentException()
		}
	}
}

private class MoveCommand(val dir:  Direction, val dist: Int) {

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
			val dir =  Direction.from(s[0])
			val dist = s.drop(1).toInt()
			return  MoveCommand(dir, dist)
		}
	}
}

private fun String.toCommands() = splitToSequence(",").map { it.trim() }.map { MoveCommand.from(it) }

fun star6() {
	println("day 3 star 6")

	val world = World()
	world.addLine(line1Input.toCommands())
	world.addLine(line2Input.toCommands())

	val result = world.crossings.map{ world[it] }.filter { it.line2Length >= 0 && it.line2Length >= 0 }
			.map { it.line1Length + it.line2Length }.min()
	println("fewest steps to intersection is $result")
}