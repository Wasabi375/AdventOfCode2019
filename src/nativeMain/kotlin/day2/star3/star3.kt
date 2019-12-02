package day2.star3

class Program(initialState: List<Int>){

	private val state: MutableList<Int> = MutableList(initialState.size) { initialState[it] }

	constructor(intProg: String): this(intProg.splitToSequence(",").map { it.trim().toInt() }.toMutableList())

	operator fun get(pos: Int): Int = state[pos]
	operator fun set(pos: Int, value: Int) {
		state[pos] = value
	}

	fun getState(): List<Int> = state

	var opPos = 0
		private set

	var done: Boolean = false
		private set

	fun executeNext() {
		check(!done)

		val opCode = get(opPos)
		val arg1 = get(get(opPos + 1))
		val arg2 = get(get(opPos + 2))
		val targetPos = get(opPos + 3)

		var paraCount: Int = 3

		when(opCode) {
			1 -> set(targetPos, arg1 + arg2)
			2 -> set(targetPos, arg1 * arg2)
			99 -> {
				done = true
				paraCount = 0
			}
			else -> throw IllegalStateException("Unexpeced opCode of $opCode at $opPos")
		}
		opPos += paraCount + 1
	}
	fun run() {
		while(!done) {
			executeNext()
		}
	}
}

fun star3() {
	println("day 2 star 3")
	val prog = Program(input)
	prog[1] = 12
	prog[2] = 2
	prog.run()
	println("Program state[0] = ${prog[0]}")

}