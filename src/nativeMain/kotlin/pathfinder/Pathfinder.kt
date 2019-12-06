package pathfinder

import collection.*


interface PathFindingNode<Self: PathFindingNode<Self>> {

	val neighbours: List<Self>

	fun costTo(neighbour: Self): Float

	fun estimateCostTo(node: Self): Float
}

private data class Node<A: PathFindingNode<A>>(val info: A, val distance: Float, val from: Node<A>?, val target: A) : Comparable<Node<A>> {

	val neighbours get() = info.neighbours
	fun costTo(n: A) = info.costTo(n)
	fun estimateCostTo(n: A) = info.estimateCostTo(n)

	val estimatedTotalDistance by lazy{ distance + estimateCostTo(target) }

	override fun compareTo(other: Node<A>): Int {
		return estimatedTotalDistance.compareTo(other.estimatedTotalDistance)
	}
}

fun <A: PathFindingNode<A>>findPath(start: A, end: A): List<A>{

	val openList = SortedQueue<Node<A>>(Comparator { a, b -> a.compareTo(b) })

	val existingNodes = mutableMapOf<A, Node<A>>()

	val closedSet = mutableSetOf<A>()

	fun addNode(n: Node<A>) {
		openList.add(n)
		existingNodes[n.info] = n
	}

	openList.add(Node(start, 0f, null, end))

	while(true) {

		val current = openList.poll() ?: return emptyList()

		if(current.info == end) return solve(current)

		closedSet.add(current.info)

		current.neighbours
				.filter { it !== current.info }
				.filterNot { it in closedSet }
				.map {
					val newDistance = current.distance + current.costTo(it)
					if(existingNodes.containsKey(it)) {
						val old = existingNodes[it]!!
						if(old.distance < newDistance) old
						else old.copy(from = current, distance = newDistance)
					} else {
						Node(it, newDistance, current, end)
					}
				}
				.forEach (::addNode)
	}
}

private fun <A: PathFindingNode<A>>solve(n: Node<A>): List<A> {
	val result = mutableListOf<A>()

	var c: Node<A>? = n
	while(c != null){
		result.add(c.info)
		c = c.from
	}
	return result
}
