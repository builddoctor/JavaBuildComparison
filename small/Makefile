%.class: %.java
	javac $<

clean:
	find . -name \*.class -exec rm {} \;
	rm -f classes.list
	rm -rf reports
	rm -rf willy.jar

classes.list:
	find . -name '*.class' -print > classes.list


test: classes.list
	java -cp lib/junit-4.9.jar org.junit.runner.JUnitCore < classes.list

pkg: classes.list
	jar cf willy.jar @classes.list

all: clean src/main/java/com/builddoctor/HelloWorld.class test pkg

.DEFAULT_GOAL := all
