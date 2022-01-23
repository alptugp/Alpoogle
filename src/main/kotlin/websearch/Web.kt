package websearch

import org.jsoup.Jsoup.connect
import org.jsoup.nodes.Document

class URL(val urlStr: String) {
  override fun toString(): String = urlStr

  override fun equals(other: Any?): Boolean {
    return if (other is URL) {
      this.urlStr == other.urlStr
    } else { false }
  }

  fun download(): WebPage {
    return WebPage(connect(urlStr).get())
  }
}

class WebPage(val doc: Document) {
  fun extractWords(): List<String> = doc.text().lowercase()
    .filter({ !(it.toString().equals(".") || it.toString().equals(","))})
    .split(" ")

  fun extractLinks(): List<URL> = doc
    .getElementsByTag("a")
    .map { URL(it.attr("href")) }
    .filter { it.urlStr.startsWith("http://") || it.urlStr.startsWith("https://")
    }
}