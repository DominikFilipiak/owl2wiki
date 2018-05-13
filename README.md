Generating Semantic Media Wiki Content from Domain Ontologies [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
========

Automatic OWL ontology conversion to articles in Semantic Media Wiki system. Our implemented prototype converts a branch of an ontology rooted at the user defined class into Wiki articles and categories. The final result is defined by templates expressed in the Wiki markup language. The tests show that our solution can be used for fast bootstrapping of Semantic Media Wiki content from OWL files. More details can be found [here](http://ceur-ws.org/Vol-1275/swcs2014_submission_5.pdf). Written for research purposes at Poznań University of Technology. Tested with [DMOP ontology](http://www.dmo-foundry.org/DMOP).

If you're going to re-use my work, please cite it accordingly:
> Dominik Filipiak and Agnieszka Ławrynowicz. 2014. Generating semantic media Wiki content from domain ontologies. In Proceedings of the Third International Conference on Semantic Web Collaborative Spaces - Volume 1275 (SWCS'14), Pascal Molli, John Breslin, and Maria-Esther Vidal (Eds.), Vol. 1275. CEUR-WS.org, Aachen, Germany, Germany, 68-76.

## Installation
To build the package, run the command below:
```bash
mvn clean package -DskipTests=true
```
Apparently I commited some failing tests, we need to skip that. 🌧

## Example
Set up your Semantic Media Wiki. Download [the DMOP ontology](http://www.dmo-foundry.org/DMOP), which classifies data mining algorithms. Here's the *DMKB.owl* (viewed in Protege), we're interested in pushing all algorithms to the SemanticMediaWiki instance in this example.
![DMOP](https://i.imgur.com/jHRsC1T.png)


Now go to the *target* folder, and then to *conf* - you need to change your configuration files. There are three: application.properties, mapping.properties, and template.
1) General properties (*application.properties*) - paths to other files and SMW-related settings. For the DMOP example, you have to change *wiki.password*, *wiki.userName*, *wiki.url*, and *owl.files* according to your setup.
```INI
path.template=./conf/template	 # path to your template - there's an example for DMOP
path.mapping=./conf/mapping.properties	# path to your mapping properties - there's an example for DMOP
wiki.password=  # password for your Semantic Media Wiki admin
wiki.userName=admin	 # your SMW user
wiki.url=http\://127.0.0.1/semanticmediawiki/ # your SMW instance address
owl.rootRDFType=http\://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl\#DM-Algorithm # Your root RDF type - the one you're going to transfer to SMW
owl.files=/somepath/DMOP/DMKB.owl	 # The file you're going to process
```
2) Mapping properties is defined in *mapping.properties*. No changes are needed for DMOP
```INI
definition=http\://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl\#definition
specifiesInputClass=http\://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl\#specifiesInputClass
specifiesOutputClass=http\://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl\#specifiesOutputClass
assumes=http\://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl\#assumes
hasOptimizationProblem=http\://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl\#hasOptimizationProblem
Qualities=http\://www.loa-cnr.it/ontologies/DOLCE-Lite.owl\#has-quality
```
3) The variables form mapping are later used for the *template*, which is written in wiki markup language. It specifies how the article should look like (notably its infobox).
```MediaWiki
{| align=right class="wikitable"
|+Algorithm
|-
! scope="col"| Property
! scope="col"| Value
|-

| specifiesInputClass
| {{#arraymap:{{{specifiesInputClass|}}}|,|x|[[specifiesInputClass::x]]| \n\n }}
|-

| specifiesOutputClass
| {{#arraymap:{{{specifiesOutputClass|}}}|,|x|[[specifiesOutputClass::x]]| \n\n }}
|-

| assumes
| {{#arraymap:{{{assumes|}}}|,|x|[[assumes::x]]| \n\n }}
|-

| hasOptimizationProblem
| {{#arraymap:{{{hasOptimizationProblem|}}}|,|x|[[hasOptimizationProblem::x]]| \n\n }}
|-

| has-quality
| {{#arraymap:{{{Qualities|}}}|,|x|[[has-quality::x]]| \n\n }}
|}

{{{definition}}}
```

With all that set, simply run:
```bash
java -Dconfig=file:conf/application.properties -Xmx1024M -jar owl2wiki-jar-with-dependencies.jar
```
...and that's all. Adjust *-Xmx* if you're out of RAM. Below some generated examples:
![DM-Algorithm](https://i.imgur.com/oSc9mL4.png)
![C4.5](https://i.imgur.com/IyxadVs.png)
