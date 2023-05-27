package soy.gabimoreno.turbineexample.domain

import javax.inject.Inject

class GetRandomBooleanUseCase @Inject constructor() {
    operator fun invoke(): Boolean {
        val value = (0..1).random()
        return value != 0
    }
}
