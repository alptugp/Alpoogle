class SearchEngine(val map: Map<URL, WebPage>) {
  private var index: Map<String, List<SearchResult>> = mapOf()

  fun compileIndex() {
    var lst: MutableList<Pair<String, URL>> = mutableListOf()
    for (pair in map) {
      for (word in pair.value.extractWords()) {
        lst.add(Pair(word, pair.key))
      }
    }
    index = lst.groupBy { it.first }.mapValues { rank(it.value.unzip().second) }
  }

  private fun rank(urls: List<URL>): List<SearchResult> {
    var lst: MutableList<SearchResult> = mutableListOf()
    for (group in urls.groupBy { it }) {
      lst.add(SearchResult(group.key, group.value.size))
    }
    return lst.sortedByDescending { it.count }
  }

  fun searchFor(query: String): SearchResultsSummary {
    return SearchResultsSummary(query, index.getOrDefault(query, listOf(SearchResult(URL("No URL address found for this word"), 0))))
  }
}

class SearchResult(val url_search: URL, val count: Int)

class SearchResultsSummary(val searchedWord: String, val url_results: List<SearchResult>) {
  override fun toString(): String {
    val sb = StringBuilder()
    sb.append("Results for \"${searchedWord}\":\n")
    for (result in url_results) {
      sb.append("  ${result.url_search} - ${result.count}")
    }
    return sb.toString()
  }
}

fun main() {

}


