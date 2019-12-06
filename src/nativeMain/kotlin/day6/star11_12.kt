package day6

import pathfinder.*

private class SpaceObject(
		val name: String,
		var orbits: SpaceObject? = null,
		val orbitedBy: MutableList<SpaceObject> = mutableListOf())
	: PathFindingNode<SpaceObject> {

	override fun equals(other: Any?): Boolean {
		return other != null && other is SpaceObject && other.name == name
	}

	override fun hashCode(): Int {
		return name.hashCode()
	}

	private var orbitCount: Int = -1

	fun countOrbits(): Int {
		if(orbitCount >= 0) return orbitCount
		orbitCount = generateSequence(this) { it.orbits }.count()
		return orbitCount
	}

	override val neighbours: List<SpaceObject> by lazy { (orbitedBy + orbits).filterNotNull() }

	override fun costTo(neighbour: SpaceObject): Float = 1f

	override fun estimateCostTo(node: SpaceObject): Float = 0f
}

fun <A> Pair<A, A>.toSequence() = sequence {
	yield(first)
	yield(second)
}

fun star11() {
	println("day6 star 11")

	val orbits = calcOrbits(input)

	val totalOrbits = orbits.values.sumBy { it.countOrbits() }

	println("total Orbit count: $totalOrbits")
}

private fun calcOrbits(input: Sequence<String>): Map<String, SpaceObject> {
	val orbitPairs = input
			.map { it.split(")").also { pair -> assert(pair.size == 2) } }
			.map { Pair(it[0], it[1]) }

	val objects = orbitPairs
			.flatMap { it.toSequence() }
			.toSet()
			.associateWith { SpaceObject(it) }

	orbitPairs
			.map { objects.getValue(it.first) to objects.getValue(it.second) }
			.forEach { (center, orbiter) ->
				center.orbitedBy.add(orbiter)
				assert(orbiter.orbits == null)
				orbiter.orbits = center
			}
	return objects
}

fun star12() {

	println("day 6 star 12")

	val orbits = calcOrbits(input)

	val start = orbits.getValue("YOU").orbits!!
	val end = orbits.getValue("SAN").orbits!!

	val path = findPath(start, end)

	println("number of orbit switches is ${path.size - 1}")
}