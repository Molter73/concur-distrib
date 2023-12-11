all: latex

latex: semaphore-and-monitor.tex output-dir
	tectonic -X compile \
		--synctex \
		--keep-logs \
		--keep-intermediates \
		--outdir build/ \
		semaphore-and-monitor.tex

output-dir:
	@mkdir -p build/

clean:
	rm -rf build/
