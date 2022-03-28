import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.ImagePlus;
import ij.WindowManager;
import ij.IJ;

public class unir_canais implements PlugIn{

	public void run(String arg) {
		
		unir_imagens_para_RGB();
		
	}

	private void unir_imagens_para_RGB() {
		
		//criando um vetor com os titulos das imagens abertas
		String imagens_abertas[] = WindowManager.getImageTitles();
		
		//criando as imagens com os canais separados
		ImagePlus imagem_RED = null;
		ImagePlus imagem_GREEN = null;
		ImagePlus imagem_BLUE = null;
		
	
		//loop para fazer a correspondencia entre uma imagem aberta e seu objeto
		for(int x = 0; x < imagens_abertas.length; x++) {
			if(imagens_abertas[x].contains("RED")) {
				imagem_RED = WindowManager.getImage(imagens_abertas[x]);
			}
			if(imagens_abertas[x].contains("GREEN")) {
				imagem_GREEN = WindowManager.getImage(imagens_abertas[x]);
			}	
			if(imagens_abertas[x].contains("BLUE")) {
				imagem_BLUE = WindowManager.getImage(imagens_abertas[x]);	
			}
		}
		
		//criando a imagem RGB que vai receber o resultado da união das três separadas
		ImagePlus imagem_RGB = IJ.createImage("RGB", imagem_RED.getWidth(), imagem_RED.getHeight(), 0, 24);
		
		ImageProcessor processor_vermelho = imagem_RED.getProcessor(),
					   processor_verde = imagem_GREEN.getProcessor(),
					   processor_azul = imagem_BLUE.getProcessor(),
					   processor_RGB = imagem_RGB.getProcessor();
		
		
		int valorPixel[] = {0,0,0};
		
		for(int i = 0; i < imagem_RED.getWidth(); i++)
		{
			for(int j = 0; j < imagem_RED.getHeight(); j++) {
				
				valorPixel[0] = processor_vermelho.getPixel(i, j);;
				valorPixel[1] = processor_verde.getPixel(i, j);
				valorPixel[2] = processor_azul.getPixel(i, j);
				
				processor_RGB.putPixel(i, j, valorPixel);
			}
		}
		
		imagem_RGB.updateAndDraw();
		imagem_RGB.show();
		
		
	}

}
