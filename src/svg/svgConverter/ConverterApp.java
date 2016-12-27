package svg.svgConverter;



import java.util.regex.Pattern;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.fop.svg.PDFTranscoder;


public class ConverterApp {
	public static void main(String[] args) {
		// http://stackoverflow.com/questions/367706/how-to-parse-command-line-arguments-in-java
		Options options = new Options();

        Option input = new Option("i", "input", true, "Input file path.");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "Output file path.");
        output.setRequired(false);
        options.addOption(output);
        
        Option bmp = new Option("bmp", "bmp", true, "Convert svg to bmp.");
        bmp.setRequired(false);
        bmp.setOptionalArg(true);
        options.addOption(bmp);
        
        Option pdf = new Option("pdf", "pdf", true, "Convert svg to pdf.");
        pdf.setRequired(false);
        pdf.setOptionalArg(true);
        options.addOption(pdf);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Help", options);

            System.exit(1);
            return;
        }
        Transcoder transcoder = new PNGTranscoder();
        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = inputFilePath.split(Pattern.quote("."))[0] + ".";
        
        if (cmd.hasOption("bmp")) {
        	transcoder = new PNGTranscoder();
        	outputFilePath = outputFilePath + "bmp";
        }
        if (cmd.hasOption("pdf")) {
        	transcoder = new PDFTranscoder();
        	outputFilePath = outputFilePath + "pdf";
        }

        
        if (cmd.hasOption("output")) {
        	outputFilePath = cmd.getOptionValue("output");
        }

		IConverter converter = new TranscoderConverter(inputFilePath, outputFilePath, transcoder);
		try {
			converter.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 
 
