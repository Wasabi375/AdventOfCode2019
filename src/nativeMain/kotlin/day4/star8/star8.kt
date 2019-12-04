package day4.star8

import day4.star7.toDigits

fun List<Int>.isValid(): Boolean {
	var last: Int = -1
	var repeatCount = 0
	var hasDuplicate = false

	for(i in this) {
		if(i < last) return false
		if(i == last) {
			repeatCount++
		}
		else {
			if(repeatCount == 1){
				hasDuplicate = true
			}
			repeatCount = 0
		}
		last = i
	}
	return hasDuplicate || repeatCount == 1
}

fun star8(){
	println("day 4 star 8")
	var validCount = 0

	assert(112233.toDigits().isValid())
	assert(!123444.toDigits().isValid())
	assert(111122.toDigits().isValid())

	for(i in 128392..643281){
		if(i.toDigits().isValid()) validCount++
	}
	println("The number of valid combinations is $validCount")
}