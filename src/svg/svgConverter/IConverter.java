package svg.svgConverter;

import java.io.FileNotFoundException;

import org.apache.batik.transcoder.TranscoderException;

public interface IConverter {
	public String getInputFilename();
	public String getOutputFilename();
	
	public void convert() throws Exception;
}