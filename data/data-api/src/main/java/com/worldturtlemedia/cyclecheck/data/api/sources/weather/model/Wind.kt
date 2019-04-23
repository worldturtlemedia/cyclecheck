package com.worldturtlemedia.cyclecheck.data.api.sources.weather.model

data class Wind(
    val speed: Double,
    val degree: Int
) {

    val direction: WindDirection
        get() = WindDirection.degreeToCardinalDirection(degree)
}

sealed class WindDirection(val shortCode: String) {
    object North : WindDirection("N")
    object NorthByNorthEast : WindDirection("NNE")
    object NorthEast : WindDirection("NE")
    object EastByNorthEast : WindDirection("ENE")
    object East : WindDirection("E")
    object EastBySouthEast : WindDirection("ESE")
    object SouthEast : WindDirection("SE")
    object SouthBySouthEast : WindDirection("SSE")
    object South : WindDirection("S")
    object SouthBySouthWest : WindDirection("SSW")
    object SouthWest : WindDirection("SW")
    object WestBySouthWest : WindDirection("WSW")
    object West : WindDirection("W")
    object WestByNorthWest : WindDirection("WNW")
    object NorthWest : WindDirection("NW")
    object NorthByNorthWest : WindDirection("NNW")

    companion object {

        private val asList: List<WindDirection> = listOf(
            North,
            NorthByNorthEast,
            NorthEast,
            EastByNorthEast,
            East,
            EastBySouthEast,
            SouthEast,
            SouthBySouthEast,
            South,
            SouthBySouthWest,
            SouthWest,
            WestBySouthWest,
            West,
            WestByNorthWest,
            NorthWest,
            NorthByNorthWest
        )

        fun degreeToCardinalDirection(degree: Int): WindDirection {
            val index = Math.floor((degree / 22.5) + 0.5) % asList.size
            return asList[index.toInt()]
        }
    }
}
