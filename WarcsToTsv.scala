/*
 * Scala Scripts for Warcbase: an open-source platform for managing web archives
 *
 * Run by pasting into the REPL. ":paste" to initiate and "Ctrl+D" to execute once code is pasted
 */

// Adjust the path to the elections WARC directory
import java.io.File
import scala.collection.mutable.ArrayBuffer

val files = new java.io.File("/Volumes/Transcend/warc-data/el04").listFiles.filter(_.getName.endsWith(".gz"))
var arcs = ArrayBuffer[String]()

// Need a new Array to run next part of the script
for (i <- 0 until files.length) {
  arcs += files(i).toString()
}

// warcbase at work!
import org.warcbase.spark.rdd.RecordRDD._
import org.warcbase.spark.matchbox.{RecordLoader, ExtractDomain, ExtractLinks, ExtractBoilerpipeText}
import org.warcbase.spark.matchbox.TupleFormatter._

for (i <- arcs) {
  RecordLoader.loadArchives(i, sc)
  .keepValidPages()
  .map(r => (r.getCrawlDate, r.getDomain, r.getUrl, ExtractBoilerpipeText(r.getContentString)))
  .map(tabDelimit(_))
  .saveAsTextFile(i + "-tsv")  // append a "-tsv" to create the dir names. TSV will be in the part-00000 file of each dir
}



