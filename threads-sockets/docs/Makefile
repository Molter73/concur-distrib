all: latex

latex: threads-and-sockets.tex uml-diagrams output-dir
	tectonic -X compile \
		--synctex \
		--keep-logs \
		--keep-intermediates \
		--outdir build/ \
		threads-and-sockets.tex

output-dir:
	@mkdir -p build/

uml-diagrams: uml.md
	plantuml -tlatex:nopreamble uml.md -o build

clean:
	rm -rf build/
