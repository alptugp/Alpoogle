import org.jsoup.nodes.Document

class URL(val urlStr: String) {
  override fun toString(): String = urlStr
}

class WebPage(val doc: Document) {
  fun extractWords(): List<String> = doc.text().lowercase().filter({ !(it.equals(".") && it.equals(","))}).split(" ")
}


