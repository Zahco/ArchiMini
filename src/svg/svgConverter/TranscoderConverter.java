package svg.svgConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

public class TranscoderConverter extends AbstractConverter {
	
	private Transcoder transcoder;
	
	public TranscoderConverter(String inputFilename, String outputFilename, Transcoder transcoder) {
		super(inputFilename, outputFilename);
		this.transcoder = transcoder;
	}
	
	public Transcoder getTranscoder() {
		return transcoder;
	}
	
	// http://stackoverflow.com/questions/6875807/convert-svg-to-pdf
	public void convert() throws Exception {
		TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(new File(getInputFilename())));
        TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(new File(getOutputFilename())));
        getTranscoder().transcode(transcoderInput, transcoderOutput);
	}
}