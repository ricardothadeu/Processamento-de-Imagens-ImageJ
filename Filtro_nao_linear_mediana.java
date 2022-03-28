import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.IJ;
import ij.ImagePlus;
import java.util.Arrays; 

public class Filtro_nao_linear_mediana implements PlugIn {

	public void run (String arg) {
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processor = imagem.getProcessor();
		ImageProcessor auxiliar = processor.duplicate();
		
		
		filtro_mediana(processor, auxiliar);
		
		imagem.setProcessor(auxiliar);
		imagem.updateAndDraw();
	}

	private void filtro_mediana(ImageProcessor processor, ImageProcessor auxiliar) {
		
		int altura = processor.getHeight();
		int largura = processor.getWidth();
		
		int vizinhanca[] = new int[9];
		int pixel;
		
		
		for(int i = 1; i < largura-1; i++)
		{
			for(int j = 1; j < altura-1; j++)
			{
				int posicao = 0;
				
				for(int x = -1; x <= 1; x++)
				{
					for(int y = -1; y <= 1; y++)
					{
						vizinhanca[posicao] = processor.getPixel(i + x, j + y);
						posicao++;
					}
				}
				
			Arrays.sort(vizinhanca);
				
			pixel = vizinhanca[vizinhanca.length / 2];
				
			auxiliar.putPixel(i, j, pixel);
			}
		}	
	}
	
	
}