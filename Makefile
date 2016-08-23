
JFLAGS = -g
JCC = javac

classes = DNA.java

run: dna

dna:
	$(JCC) $(JFLAGS) $(classes)

1: dna
	java DNA test1.txt

2: dna
	java DNA test2.txt

clean:
	rm -rf *.class
