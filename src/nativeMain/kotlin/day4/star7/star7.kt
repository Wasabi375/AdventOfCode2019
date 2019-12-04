package day4.star7

fun Int.toDigits(): List<Int>{
	val result = mutableListOf<Int>()

	var current = this
	while(current != 0) {
		val start = current
		current /= 10
		current *= 10
		result.add(start - current)
		current /= 10
	}
	return result.asReversed()
}

fun List<Int>.isValid(): Boolean {
	var last: Int = -1
	var hasDuplicate = false
	for(i in this) {
		if(i < last) return false
		if(i == last) hasDuplicate = true
		last = i
	}
	return hasDuplicate
}

fun star7() {
	println("day 4 star 7")

	assert(111111.toDigits().isValid())
	assert(!223450.toDigits().isValid())
	assert(!123789.toDigits().isValid())

	var validCount = 0

	for(i in 128392..643281){
		if(i.toDigits().isValid()) validCount++
	}
	println("The number of valid combinations is $validCount")
}
