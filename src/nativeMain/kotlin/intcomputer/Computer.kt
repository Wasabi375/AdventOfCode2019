package intcomputer

class Computer(initialState: List<Int>, val allowedCodes: Set<Int>? = null){

	var inputFun: () -> Int = {
		var result: Int? = null
		while(result == null) {
			println("input number: ")
			result = kotlin.io.readLine()!!.toIntOrNull()
			if(result != null) print("Not a number! ")
		}
		result
	}

	var outputFun: (Int) -> Unit =  {
		println(it)
	}

	constructor(intProg: String, allowedCodes: Set<Int>? = null) :
			this(intProg.splitToSequence(",")
					     .map { it.trim().toInt() }
					     .toMutableList(),
			     allowedCodes)

	enum class ParameterMode {
		Position,
		Immediate;

		companion object {
			fun from(i: Int) = when(i) {
				0 -> Position
				1 -> Immediate
				else -> throw IndexOutOfBoundsException("$i is not in 0..1")
			}
		}
	}

	data class OpCode(val opCode: Int, val modes: List<ParameterMode>) {
		companion object{
			fun from(i: Int): OpCode {
				val opCode = i % 100
				val modesValue = i / 100
				val modes = "${modesValue}0".reversed().padEnd(8, '0')
						.map { it.toString().toInt() }
						.map { ParameterMode.from(it) }

				return OpCode(opCode, modes)
			}
		}
	}

	private val state: MutableList<Int> = MutableList(initialState.size) { initialState[it] }

	operator fun get(pos: Int): Int = state[pos]
	operator fun set(pos: Int, value: Int) {
		state[pos] = value
	}

	fun getState(): List<Int> = state

	var instructionPointer = 0
		private set

	var done: Boolean = false
		private set

	fun executeNext() {
		check(!done)

		val opCode = OpCode.from(get(instructionPointer))
		if(allowedCodes != null) check(opCode.opCode in allowedCodes)

		val paraCount = when(opCode.opCode) {
			1 -> add(opCode)
			2 -> mul(opCode)
			3 -> input(opCode)
			4 -> output(opCode)
			5 -> jumpIfTrue(opCode)
			6 -> jumpIfFalse(opCode)
			7 -> lessThan(opCode)
			8 -> equalsOp(opCode)
			99 -> {
				done = true
				0
			}
			else -> throw IllegalStateException("Unexpeced opCode of $opCode at $instructionPointer")
		}
		instructionPointer += paraCount + 1
	}

	private fun getValue(offset: Int, opCode: OpCode): Int {
		return when (opCode.modes[offset]) {
			ParameterMode.Position       -> {
				get(getDirect(offset))
			}
			ParameterMode.Immediate -> {
				getDirect(offset)
			}
		}
	}

	fun getDirect(offset: Int): Int {
		require(offset > 0)
		return get(instructionPointer + offset)
	}

	private fun add(opCode: OpCode): Int {
		require(opCode.opCode == 1)

		val arg1 = getValue(1, opCode)
		val arg2 = getValue(2, opCode)
		val targetPos = getDirect(3)

		set(targetPos, arg1 + arg2)

		return 3
	}

	private fun mul(opCode: OpCode): Int {
		require(opCode.opCode == 2)

		val arg1 = getValue(1, opCode)
		val arg2 = getValue(2, opCode)
		val targetPos = getDirect(3)

		set(targetPos, arg1 * arg2)

		return 3
	}

	@Suppress("UNUSED_PARAMETER")
	private fun input(opCode: OpCode): Int {
		require(opCode.opCode == 3)

		val value = inputFun()
		val targetPos = getDirect(1)

		set(targetPos, value)
		return 1
	}

	private fun output(opCode: OpCode): Int {
		require(opCode.opCode == 4)

		val value = getValue(1, opCode)
		outputFun(value)
		return 1
	}

	private fun jumpIfTrue(opCode: OpCode): Int {
		require(opCode.opCode == 5)

		val condition = getValue(1, opCode)
		val target = getValue(2, opCode)
		return if(condition != 0) {
			instructionPointer = target
			-1
		} else {
			2
		}
	}

	private fun jumpIfFalse(opCode: OpCode): Int {
		require(opCode.opCode == 6)

		val condition = getValue(1, opCode)
		val target = getValue(2, opCode)
		return if(condition == 0) {
			instructionPointer = target
			-1
		} else {
			2
		}
	}

	private fun lessThan(opCode: OpCode): Int {
		require(opCode.opCode == 7)

		val para1 = getValue(1, opCode)
		val para2 = getValue(2, opCode)
		val targetPos = getDirect(3)
		val result = if(para1 < para2) 1 else 0

		set(targetPos, result)

		return 3
	}

	private fun equalsOp(opCode: OpCode): Int {
		require(opCode.opCode == 8)

		val para1 = getValue(1, opCode)
		val para2 = getValue(2, opCode)
		val targetPos = getDirect(3)
		val result = if(para1 == para2) 1 else 0

		set(targetPos, result)

		return 3
	}

	fun run() {
		while(!done) {
			executeNext()
		}
	}
}