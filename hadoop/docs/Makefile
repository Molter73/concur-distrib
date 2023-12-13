all: latex

latex: hadoop.tex output-dir
	tectonic -X compile \
		--synctex \
		--keep-logs \
		--keep-intermediates \
		--outdir build/ \
		hadoop.tex

output-dir:
	@mkdir -p build/

clean:
	rm -rf build/
