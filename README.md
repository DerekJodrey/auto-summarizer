# Auto text summarizer

Application that takes in a file, does some next-leve-CSI-enhance magic and returns a summary. It's currently implemented with a simple ranking algorithm.  

Under development... 

### To-Do
Since the core functionality is implemented, further improvements can be made on top. This includes (listed after most likely to be done first)...
* [x] Generate Javadocs
* [ ] Generate executable
* [ ] Analyze text reduction
* [ ] Write final summary to a file
* [ ] Import text from other file formats (currently only .txt)
* [ ] Create GUI
* [ ] Generate UML diagram

### Documentation
Documentation can be found in [docs/](./docs) with entry point *index.html* at the root level. 

### Files
* file_x.txt is a file with dummy text. x is the language code according to the [ISO 639-1 standard](https://en.wikipedia.org/wiki/ISO_639-1). 
* stopwords-x.txt is a list of stop-words for respective language. 