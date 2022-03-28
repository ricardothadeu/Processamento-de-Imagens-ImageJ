import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;
import ij.ImagePlus;
import ij.IJ;

public class separar_canais_lut implements PlugIn {
	
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
		
		aplicar_LUT(canal_vermelho, canal_verde, canal_azul);
		
	
		canal_vermelho.show();
		canal_verde.show();
		canal_azul.show();
		
		
	}
	
	public void aplicar_LUT (ImagePlus canal_vermelho, ImagePlus canal_verde, ImagePlus canal_azul) {
		
		//criando os vetores red, green e blue para serem usados na LUT
		byte[] 	r1 = new byte[256], r2 = new byte[256],r3 = new byte[256],
				g1 = new byte[256], g2 = new byte[256],g3 = new byte[256],
				b1 = new byte[256], b2 = new byte[256],b3 = new byte[256];
		
		for(int i = 0; i < 256; i++)
		{
			//o LUT da imagem canal vermelho terá só valores no vetor red. A mesma logica se aplica para os outros vetores
			r1[i] = (byte)i;
			g1[i] = 0;
			b1[i] = 0;
			
			//imagem verde
			r2[i] = 0;
			g2[i] = (byte)i;
			b2[i] = 0;
			
			//imagem azul
			r3[i] = 0;
			g3[i] = 0;
			b3[i] = (byte)i;
		}
		
		LUT lut_vermelho = new LUT(r1, g1, b1),
			lut_verde = new LUT(r2,g2,b2),
			lut_azul = new LUT(r3,g3,b3);
		
		
		canal_vermelho.setLut(lut_vermelho);
		canal_verde.setLut(lut_verde);
		canal_azul.setLut(lut_azul);
	}
}
