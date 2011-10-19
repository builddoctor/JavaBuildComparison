%.class: %.java
	javac $<

clean:
	find . -name \*.class -exec rm {} \;

.DEFAULT_GOAL := src/main/java/com/builddoctor/HelloWorld.class
