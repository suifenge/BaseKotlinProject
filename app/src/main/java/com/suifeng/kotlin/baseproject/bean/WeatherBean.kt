package com.suifeng.kotlin.baseproject.bean


/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
data class WeatherBean(
    val error_code: Int,
    val reason: String,
    val result: Result,
    val resultcode: String
)

data class Result(
    val future: Future,
    val sk: Sk,
    val today: Today
)

data class Future(
    val day_20210426: Day20210426,
    val day_20210427: Day20210427,
    val day_20210428: Day20210428,
    val day_20210429: Day20210429,
    val day_20210430: Day20210430,
    val day_20210501: Day20210501,
    val day_20210502: Day20210502
)

data class Sk(
    val humidity: String,
    val temp: String,
    val time: String,
    val wind_direction: String,
    val wind_strength: String
)

data class Today(
    val city: String,
    val comfort_index: String,
    val date_y: String,
    val dressing_advice: String,
    val dressing_index: String,
    val drying_index: String,
    val exercise_index: String,
    val temperature: String,
    val travel_index: String,
    val uv_index: String,
    val wash_index: String,
    val weather: String,
    val weather_id: WeatherIdXXXXXXX,
    val week: String,
    val wind: String

) {
    override fun toString(): String {
        return "Today(city='$city', comfort_index='$comfort_index', date_y='$date_y', dressing_advice='$dressing_advice', dressing_index='$dressing_index', drying_index='$drying_index', exercise_index='$exercise_index', temperature='$temperature', travel_index='$travel_index', uv_index='$uv_index', wash_index='$wash_index', weather='$weather', weather_id=$weather_id, week='$week', wind='$wind')"
    }
}

data class Day20210426(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherId,
    val week: String,
    val wind: String
)

data class Day20210427(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherIdX,
    val week: String,
    val wind: String
)

data class Day20210428(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherIdXX,
    val week: String,
    val wind: String
)

data class Day20210429(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherIdXXX,
    val week: String,
    val wind: String
)

data class Day20210430(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherIdXXXX,
    val week: String,
    val wind: String
)

data class Day20210501(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherIdXXXXX,
    val week: String,
    val wind: String
)

data class Day20210502(
    val date: String,
    val temperature: String,
    val weather: String,
    val weather_id: WeatherIdXXXXXX,
    val week: String,
    val wind: String
)

data class WeatherId(
    val fa: String,
    val fb: String
)

data class WeatherIdX(
    val fa: String,
    val fb: String
)

data class WeatherIdXX(
    val fa: String,
    val fb: String
)

data class WeatherIdXXX(
    val fa: String,
    val fb: String
)

data class WeatherIdXXXX(
    val fa: String,
    val fb: String
)

data class WeatherIdXXXXX(
    val fa: String,
    val fb: String
)

data class WeatherIdXXXXXX(
    val fa: String,
    val fb: String
)

data class WeatherIdXXXXXXX(
    val fa: String,
    val fb: String
)