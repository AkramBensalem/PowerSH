package akram.bensalem.powersh.utils

import akram.bensalem.powersh.data.ValueAutoCompleteEntity

typealias CustomFilter<T> = (T, String) -> Boolean

fun <T> List<T>.asAutoCompleteEntities(filter: CustomFilter<T>): List<ValueAutoCompleteEntity<T>> {
    return map {
        object : ValueAutoCompleteEntity<T> {
            override val value: T = it

            override fun filter(query: String): Boolean {
                return filter(value, query)
            }
        }
    }
}

const val AutoCompleteSearchBarTag = "AutoCompleteSearchBar"