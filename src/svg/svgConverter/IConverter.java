package svg.svgConverter;


public interface IConverter {
	public String getInputFilename();
	public String getOutputFilename();
	
	public void convert() throws Exception;
}