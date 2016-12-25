# WhatsAnalzyer
A Java based tool to analyze the plain text chatlogs that can be exported from [WhatsApp](https://www.whatsapp.com/faq/en/general/23753886).

Output consists of following text analysis results (total and for each year/month) for:

* Message counts
* Word counts
* Sentence counts

The people to look for in the history can be passed as argument, see the example.

## Example
```
$ java -jar whatsanalyzer-1.0-SNAPSHOT.jar ~/Downloads/whatsapp.txt "2015, 2016" "Okeanos" "Hyperion"
13:10:01.272 [main] INFO  com.okeanos.whatsanalyzer.Main - Configuration is: file = '/Users/Example/Downloads/whatsapp.txt', years = [2015, 2016], people = [Okeanos, Hyperion]
13:10:01.921 [main] INFO  com.okeanos.whatsanalyzer.Main - Person[name=Okeanos,totalMessageCount=6,totalWordCount=72,totalSentenceCount=6,monthlyMessageCounts=[2015-01: 1, 2015-02: 1, 2015-03: 0, 2015-04: 1, 2015-05: 0, 2015-06: 0, 2015-07: 0, 2015-08: 0, 2015-09: 0, 2015-10: 0, 2015-11: 0, 2015-12: 0, 2016-01: 1, 2016-02: 1, 2016-03: 0, 2016-04: 1, 2016-05: 0, 2016-06: 0, 2016-07: 0, 2016-08: 0, 2016-09: 0, 2016-10: 0, 2016-11: 0, 2016-12: 0,  ],monthlyWordCounts=[2015-01: 11, 2015-02: 13, 2015-03: 0, 2015-04: 12, 2015-05: 0, 2015-06: 0, 2015-07: 0, 2015-08: 0, 2015-09: 0, 2015-10: 0, 2015-11: 0, 2015-12: 0, 2016-01: 11, 2016-02: 13, 2016-03: 0, 2016-04: 12, 2016-05: 0, 2016-06: 0, 2016-07: 0, 2016-08: 0, 2016-09: 0, 2016-10: 0, 2016-11: 0, 2016-12: 0,  ],monthlySentenceCounts=[2015-01: 1, 2015-02: 1, 2015-03: 0, 2015-04: 1, 2015-05: 0, 2015-06: 0, 2015-07: 0, 2015-08: 0, 2015-09: 0, 2015-10: 0, 2015-11: 0, 2015-12: 0, 2016-01: 1, 2016-02: 1, 2016-03: 0, 2016-04: 1, 2016-05: 0, 2016-06: 0, 2016-07: 0, 2016-08: 0, 2016-09: 0, 2016-10: 0, 2016-11: 0, 2016-12: 0,  ]]
13:10:01.939 [main] INFO  com.okeanos.whatsanalyzer.Main - Person[name=Hyperion,totalMessageCount=9,totalWordCount=101,totalSentenceCount=11,monthlyMessageCounts=[2015-01: 1, 2015-02: 1, 2015-03: 0, 2015-04: 1, 2015-05: 0, 2015-06: 0, 2015-07: 0, 2015-08: 1, 2015-09: 0, 2015-10: 0, 2015-11: 0, 2015-12: 0, 2016-01: 1, 2016-02: 1, 2016-03: 0, 2016-04: 1, 2016-05: 0, 2016-06: 0, 2016-07: 0, 2016-08: 1, 2016-09: 1, 2016-10: 0, 2016-11: 0, 2016-12: 0,  ],monthlyWordCounts=[2015-01: 13, 2015-02: 12, 2015-03: 0, 2015-04: 13, 2015-05: 0, 2015-06: 0, 2015-07: 0, 2015-08: 11, 2015-09: 0, 2015-10: 0, 2015-11: 0, 2015-12: 0, 2016-01: 13, 2016-02: 12, 2016-03: 0, 2016-04: 13, 2016-05: 0, 2016-06: 0, 2016-07: 0, 2016-08: 11, 2016-09: 3, 2016-10: 0, 2016-11: 0, 2016-12: 0,  ],monthlySentenceCounts=[2015-01: 1, 2015-02: 1, 2015-03: 0, 2015-04: 1, 2015-05: 0, 2015-06: 0, 2015-07: 0, 2015-08: 2, 2015-09: 0, 2015-10: 0, 2015-11: 0, 2015-12: 0, 2016-01: 1, 2016-02: 1, 2016-03: 0, 2016-04: 1, 2016-05: 0, 2016-06: 0, 2016-07: 0, 2016-08: 2, 2016-09: 1, 2016-10: 0, 2016-11: 0, 2016-12: 0,  ]]
13:10:01.940 [main] INFO  com.okeanos.whatsanalyzer.Main - Execution took 910 ms
```
## Tools

* Includes a self-compiled version of [jdf/cue.language](https://github.com/jdf/cue.language) (Apache 2.0 license)
* Uses Checkstyle and PMD rule sets from [Monits/static-code-analysis-plugin](https://github.com/Monits/static-code-analysis-plugin) (Apache 2.0 license) 

## License
Copyright 2016 Nikolas Grottendieck

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
