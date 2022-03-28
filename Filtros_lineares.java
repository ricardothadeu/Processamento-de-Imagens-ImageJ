import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;
import ij.IJ;
import ij.ImagePlus;

public class Filtros_lineares implements PlugIn {
	
	public void run (String arg) {
		
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processor = imagem.getProcessor();
		ImageProcessor processor_auxiliar = processor.duplicate();
		
		int[][] matriz_passa_alta = {{0,-1,0},{-1,5,-1},{0,-1,0}};
		double[][] matriz_passa_baixa = {{0.11,0.11,0.11}, {0.11,0.11,0.11}, {0.11,0.11,0.11}};
		int[][] matriz_borda_sul = {{-1,-1,-1},{1,-1,1},{1,1,1}};
		
		GenericDialog interfaceGrafica = new GenericDialog("Filtros Lineares");
		
		String[] metodos = {"passa-baixa média", "passa-alta", "borda sul"};
		
		
		interfaceGrafica.addRadioButtonGroup("Opções", metodos, 1, 3, null);
		
		
		interfaceGrafica.showDialog();

		if(interfaceGrafica.wasOKed()) {
			
			String opcao_radio = interfaceGrafica.getNextRadioButton();
			
			switch(opcao_radio) {
				case "passa-baixa média": 
					aplica_filtro(processor, processor_auxiliar, matriz_passa_baixa);
					break;
				case "passa-alta":
					aplica_filtro(processor, processor_auxiliar,matriz_passa_alta);
					break;
				case "borda sul":
					aplica_filtro(processor, processor_auxiliar, matriz_borda_sul);
					break;
			}
			
			imagem.setProcessor(processor_auxiliar);
			imagem.updateAndDraw();
		}
	}

	private void aplica_filtro(ImageProcessor processor, ImageProcessor processor_auxiliar, double[][] matriz) {
		
		int altura = processor.getHeight();
		int largura = processor.getWidth();
		int pixel = 0;
		int soma = 0;
		
		
		for(int i = 1; i < largura-1; i++)
		{	
			for(int j = 1; j < altura-1; j++)
			{
				soma = 0;
				
				for(int x = - 1; x <= 1; x++) 
				{
					for(int y = -1; y <= 1; y++)
					{
						pixel =  (int)(soma + processor.getPixel(i + x, j + y) * matriz[x + 1][y + 1]);
						
					}
				}
				
				processor_auxiliar.putPixel(i, j, pixel);			
			}	
		}
	}

	private void aplica_filtro(ImageProcessor processor, ImageProcessor processor_auxiliar, int[][] matriz) {
		int altura = processor.getHeight();
		int largura = processor.getWidth();
		int pixel = 0;
		int soma = 0;
		
		for(int i = 1; i < largura-1; i++)
		{	
			for(int j = 1; j < altura-1; j++)
			{
				soma = 0;
				
				for(int x = - 1; x <= 1; x++) 
				{
					for(int y = -1; y <= 1; y++)
					{
						pixel =  soma + processor.getPixel(i + x, j + y) * matriz[x + 1][y + 1];
						
					}
				}
				
				processor_auxiliar.putPixel(i, j, pixel);			
			}	
		}
	}

}
