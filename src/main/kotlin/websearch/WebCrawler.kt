package websearch

import org.jsoup.HttpStatusException
import org.jsoup.UnsupportedMimeTypeException
import javax.swing.text.Document

class WebCrawler(val startUrl: URL, private val maxNoOfTag: Int = 150, private val map: Map<URL, WebPage> = mapOf()) {
  fun run() {
    println(map.keys)
    val newMap = map.plus(startUrl to startUrl.download())
    for (link in startUrl.download().extractLinks())
      try {
        if (map.size == maxNoOfTag - 1) {
          break
        }
        if (!map.keys.any { it.equals(link) }) {
          WebCrawler(link, maxNoOfTag, newMap).run()
        }
      } catch (e: HttpStatusException) {
        break
      } catch (e: UnsupportedMimeTypeException) {
        break
      }
  }

  fun dump(): Map<URL, WebPage> = map
}

fun main() {
  val crawler = WebCrawler(URL("https://www.imperial.ac.uk"))
  crawler.run()

  val searchEngine = SearchEngine(crawler.dump())
  searchEngine.compileIndex()

  println(searchEngine.searchFor("news"))
}
