import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;
import ij.IJ;
import ij.ImagePlus;

public class Expansao_Equalizacao_Histograma implements PlugIn {
	
	public void run(String arg) {
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		
		GenericDialog interfaceGrafica = new GenericDialog("Expansão e Equalização de Histograma");
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processor = imagem.getProcessor();
		
		interfaceGrafica.addCheckbox("Expansão", false);
		interfaceGrafica.addCheckbox("Equalização", false);
		
		
		interfaceGrafica.showDialog();
		

		if (interfaceGrafica.wasOKed()) {

			boolean expansao = interfaceGrafica.getNextBoolean();
			boolean equalizacao = interfaceGrafica.getNextBoolean();
			
			if(expansao)
				expansao(processor);
			if(equalizacao)
				equalizacao(processor);
			
			imagem.updateAndDraw();
		}	
	}
	
	private void equalizacao(ImageProcessor processor) {
		
		int altura = processor.getHeight(),
				largura = processor.getWidth(),
				maior_valor = 0,
				pixel;
		
		int[] intensidades = new int[256]; 
		double[] probabilidades = new double[256];
		double[] prob_acumulada = new double[256];
				
		for(int i = 0; i <  largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{				
				pixel = processor.getPixel(i, j);
				if(pixel > maior_valor)
					maior_valor = pixel;
				
				intensidades[pixel]++;	
			}
		}
		for(int i = 0; i < 256; i++)
		{
			probabilidades[i] = (double)(intensidades[i] / (double)(largura * altura));
		}
		
		prob_acumulada[0] = probabilidades[0];		
		for(int i = 1; i < 256; i++)
		{
			prob_acumulada[i] = prob_acumulada[i - 1] + probabilidades[i];
		}
		
		for(int i = 0; i < 256; i++)
			prob_acumulada[i] = prob_acumulada[i] * maior_valor;
		
		for(int i = 0; i < largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{
				int novo_pixel;
				pixel = processor.getPixel(i, j);
				novo_pixel = (int)prob_acumulada[pixel];
						
				processor.set(i, j, novo_pixel);	
			}
		}
	}

	public void expansao (ImageProcessor processor) {
		
		int altura = processor.getHeight(),
			largura = processor.getWidth(),
			maior_valor = 0, menor_valor = 255,
			pixel;
		
		for(int i = 0; i <  largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{				
				pixel = processor.getPixel(i, j);
				if(pixel > maior_valor)
					maior_valor = pixel;
				if(pixel < menor_valor)
					menor_valor = pixel;
			}
		}
		
		for(int i = 0; i < largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{
				pixel = processor.getPixel(i, j);
				pixel = (int)((pixel - menor_valor) * (double)(255)/(maior_valor - menor_valor));
				
				processor.set(i, j, pixel);
			}
		}	
	}
}
