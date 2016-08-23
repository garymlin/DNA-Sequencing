
JFLAGS = -g
JCC = javac

classes = DNA.java

run: dna
	java DNA test.txt

dna:
	$(JCC) $(JFLAGS) $(classes)

clean:
	rm -rf *.class
