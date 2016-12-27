package svg.svgConverter;

import org.apache.batik.transcoder.Transcoder;

abstract class AbstractConverter implements IConverter {
	private String inputFilename;
	private String outputFilename;
	
	public AbstractConverter(String inputFilename, String outputFilename) {
		this.inputFilename = inputFilename;
		this.outputFilename = outputFilename;
	}
	
	public String getInputFilename() {
		return inputFilename;
	}
	
	public String getOutputFilename() {
		return outputFilename;
	}
}
