import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.ImagePlus;
import ij.IJ;


public class separar_imagem implements PlugIn {
	
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		
		if(imagem.getType() == ImagePlus.COLOR_RGB)
		{
			separar_canais_RGB(imagem, imagem.getTitle(), imagem.getWidth(), imagem.getHeight());
		}
		
	}
	
	public void separar_canais_RGB (ImagePlus imagem, String titulo, int largura, int altura) {
		
		ImageProcessor processador = imagem.getProcessor();

		int[] valorPixel = {0,0,0};
		
		ImagePlus canal_vermelho = IJ.createImage(titulo + " RED", largura, altura, 0, 8),
					canal_verde = IJ.createImage(titulo + " GREEN", largura, altura, 0, 8),
					canal_azul = IJ.createImage(titulo + " BLUE", largura, altura, 0, 8);
		
		ImageProcessor processador_vermelho = canal_vermelho.getProcessor(),
						processador_verde = canal_verde.getProcessor(),
						processador_azul = canal_azul.getProcessor();
		
		for(int i = 0; i < largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{	
				processador.getPixel(i, j, valorPixel);
				
				processador_vermelho.putPixel(i, j, valorPixel[0]);
				processador_verde.putPixel(i, j, valorPixel[1]);
				processador_azul.putPixel(i, j, valorPixel[2]);
				
			}
		}
				
		canal_vermelho.show();
		canal_verde.show();
		canal_azul.show();
	}
}
