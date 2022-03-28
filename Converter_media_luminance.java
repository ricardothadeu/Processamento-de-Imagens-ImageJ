 import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;
import ij.IJ;
import ij.ImagePlus;

public class Converter_media_luminance implements PlugIn {
	
	public void run(String arg) {
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		
		GenericDialog interfaceGrafica = new GenericDialog("Converter RGB para escala de cinza");
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processor = imagem.getProcessor();
		
		String metodos[] = {"Média", "Luminance Analogicas", "Luminance Digitais"};
		
		interfaceGrafica.addMessage("Plugin para converter RGB para escala de cinza");
		interfaceGrafica.addRadioButtonGroup("Opções", metodos, 3, 1, null);
		interfaceGrafica.addCheckbox("Nao alterar imagem original", false);
	
	
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasOKed()) {

			String opcao_radio = interfaceGrafica.getNextRadioButton();
			boolean altera_original = interfaceGrafica.getNextBoolean();
			
			switch(opcao_radio) {
				case "Média":
					double peso = 0.333;
					converter(processor, imagem, peso, peso, peso, altera_original);
					break;
				case "Luminance Analogicas":
					converter(processor, imagem, 0.299, 0.587, 0.114, altera_original);
					break;
				case "Luminance Digitais":
					converter(processor, imagem, 0.2125, 0.7154, 0.072, altera_original);
					break;
			}
		}
	}
	
	private void converter(ImageProcessor processor, ImagePlus imagem, double wr, double wg, double wb, boolean altera_original) {
		
		ImagePlus nova_imagem = IJ.createImage("Nova imagem", imagem.getWidth(), imagem.getHeight(), 0, 8);
		ImageProcessor processador_auxiliar = nova_imagem.getProcessor();

		double gray;
			
		int valorPixel[] = {0,0,0};
		
		for(int i = 0; i < processor.getWidth(); i++)
		{
			for(int j = 0; j < processor.getHeight(); j++)
			{
				processor.getPixel(i, j, valorPixel);
				
				gray = (valorPixel[0] * wr + valorPixel[1] * wg + valorPixel[2] * wb);
				
				processador_auxiliar.putPixelValue(i, j, gray);
			}
		}
		
		if(altera_original)
			nova_imagem.show();
		else
		{
			imagem.setProcessor(processador_auxiliar);
			imagem.updateAndDraw();
		}
	}

}
