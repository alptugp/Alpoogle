package websearch

import org.jsoup.HttpStatusException
import org.jsoup.UnsupportedMimeTypeException
import javax.swing.text.Document

class WebCrawler(val startUrl: URL, private val maxNoOfTag: Int = 4, private val map: Map<URL, WebPage> = emptyMap()) {
  fun run() {
    if (map.size < maxNoOfTag) {
    val newMap = map.plus(startUrl to startUrl.download())
    for (link in startUrl.download().extractLinks())
      try {
        if (!map.keys.any { it.equals(link) }) {
          WebCrawler(link, maxNoOfTag, newMap).run()
        }
      } catch (e: HttpStatusException) { break }
        catch (e: UnsupportedMimeTypeException) { break }
    }
  }

  fun dump(): Map<URL, WebPage> = map
}

fun main() {
  val crawler = WebCrawler(URL("https://www.imperial.ac.uk"))
  crawler.run()
}
