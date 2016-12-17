# WhatsAnalzyer
A Java based tool to analyze the plain text chatlogs that can be exported from [WhatsApp](https://www.whatsapp.com/faq/en/general/23753886).

Output consists of following text analysis results (total and for each year/month) for:

* Message counts
* Word counts
* Sentence counts

The people to look for in the history can be passed as argument, see the example.

## Example
```
$ java -jar whatsanalyzer-1.0-SNAPSHOT.jar ~/Downloads/whatsapp.txt "Example Name" "Example Name 2"
22:36:05.737 [main] INFO  com.okeanos.whatsanalyzer.Main - Configuration is: file = '/Users/Example/Downloads/whatsapp.txt', people = [Example Name, Example Name 2]
22:36:06.670 [main] INFO  com.okeanos.whatsanalyzer.Main - Person[name=Example Name,totalMessageCount=2388,totalWordCount=30744,totalSentenceCount=866,monthlyMessageCounts=[3, 107, 186, 138, 167, 298, 403, 326, 314, 242, 201, 3],monthlyWordCounts=[31, 1483, 2142, 1787, 1722, 3253, 4408, 3607, 4383, 3156, 4625, 147],monthlySentenceCounts=[1, 15, 44, 42, 35, 84, 115, 143, 123, 117, 140, 7]]
22:37:29.923 [main] INFO  com.okeanos.whatsanalyzer.Main - Person[name=Example Name 2,totalMessageCount=2032,totalWordCount=16479,totalSentenceCount=383,monthlyMessageCounts=[4, 84, 171, 133, 144, 264, 336, 256, 266, 212, 159, 3],monthlyWordCounts=[18, 825, 1436, 1253, 1369, 2409, 2170, 1546, 1985, 1349, 1976, 143],monthlySentenceCounts=[2, 19, 33, 20, 25, 62, 66, 30, 43, 46, 35, 2]]
22:36:06.670 [main] INFO  com.okeanos.whatsanalyzer.Main - Execution took 1013 ms
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
