# warcbase-scripts

This repository was created to manage part of the workflow for a team project by "Team Turtle" during the Archives Unleashed Datathon 2.0 (http://archivesunleashed.com/) held between June 13-15, 2016 at the Library of Congress in Washington DC.

This work was presented at the [Saving the Web: The Ethics and Challenges of Preserving What’s on the Internet](https://www.loc.gov/loc/kluge/news/save-web-2016.html) Symposium on June 16, 2016. 

## Workflow

Steps 1 and 4 are covered in this repository. The other steps were covered by colleagues in the group.

1. Parse and clean the text from WARCs of party/candidate websites using warcbase
2. Extracted Named Entities (candidate names, state names)
3. Topic modeling (LDA, TF-IDF)
4. Semantic Web extension - Google Knowledge Graph API and Wikidata


## 1. warcbase

The open-source warcbase repo is [here](https://github.com/lintool/warcbase).

Install and open up the warcbase REPL.

```
# Start up the warcbase REPL in the terminal
$ spark-shell --jars target/warcbase-0.1.0-SNAPSHOT-fatjar.jar

# Or start up warcbase with additional memory
$ spark-shell --driver-memory 8G --jars target/warcbase-0.1.0-SNAPSHOT-fatjar.jar

WARN  NativeCodeLoader - Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.5.2
      /_/

Using Scala version 2.10.4 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_79)
Type in expressions to have them evaluated.
Type :help for more information.

```

Once the Scala REPL is loaded, starting running the scripts in [WarcsToTsv.scala](https://github.com/nchah/warcbase-scripts/blob/master/WarcsToTsv.scala).

The script should make it possible to iterate over hundreds of WARC/ARC files and output the relevant textual data into TSV files.

```
scala> :paste
// Entering paste mode (ctrl-D to finish)

```

Then, merge all of the individual TSV files into a single master TSV with a single Bash command in [merge-tsv.sh](https://github.com/nchah/warcbase-scripts/blob/master/merge-tsv.sh).

```
$ # Merge each TSV file named "part-00000" located in distinct folders
$ cat ./*/part-00000 >merged.tsv

```


## 4. Semantic Web extension

The [kg-api.py](https://github.com/nchah/warcbase-scripts/blob/master/kg-api.py) script is a Python CLI tool to query the Google Knowledge Graph API. This script is meant to serve as a Semantic Web extension to the topic modelling and Named Entity Recognition (NER) done in steps 2 and 3 of the project workflow.


```
$ python kg-api.py 'library of congress'
Displaying results... (limit: 5)

Library of Congress
 - entity_types: Library, GovernmentOrganization, LocalBusiness, MovieTheater, Thing, Organization, Place
 - description: Federal institution
 - detailed_description: The Library of Congress is the research library that officially serves the United States Congress, b...
 - identifier: kg:/m/04jp2
 - url: http://www.loc.gov/
 - resultScore: 1727.708496

United States Copyright Office
 - entity_types: GovernmentOrganization, Thing, Organization
 - description: Government office
 - detailed_description: The United States Copyright Office, a part of the Library of Congress, is the official U.S. governme...
 - identifier: kg:/m/01h2h3
 - url: http://www.copyright.gov/
 - resultScore: 345.095276

Thomas Jefferson
 - entity_types: Person, Thing
 - description: 3rd U.S. President
 - detailed_description: Thomas Jefferson was an American Founding Father who was the principal author of the Declaration of ...
 - identifier: kg:/m/07cbs
 - url: http://www.whitehouse.gov/about/presidents/thomasjefferson
 - resultScore: 321.825714

Thomas Jefferson Building
 - entity_types: CivicStructure, Thing, TouristAttraction, Place
 - description: Library
 - detailed_description: The oldest of the three United States Library of Congress buildings, the Thomas Jefferson Building w...
 - identifier: kg:/m/09p_2j
 - url: http://www.loc.gov/loc/legacy/bldgs.html
 - resultScore: 232.273392

Library of Congress Recordings
 - entity_types: MusicAlbum, Thing
 - description: Album by Woody Guthrie
 - detailed_description: The Library of Congress Recording Sessions refers to a March 1940 session of recordings Woody Guthri...
 - identifier: kg:/m/03nrwvb
 - url: N/A
 - resultScore: 231.512314

```








