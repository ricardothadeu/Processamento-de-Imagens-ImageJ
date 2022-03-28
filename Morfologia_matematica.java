import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;
import ij.IJ;
import ij.ImagePlus;

public class Morfologia_matematica implements PlugIn {
	
	public void run (String arg) {
		
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processor = imagem.getProcessor();
		ImageProcessor auxiliar = processor.duplicate();
		
		int[][] elemento_estruturante = {{1,1,1}, {1,1,1}, {1,1,1}};
		
		GenericDialog interfaceGrafica = new GenericDialog("Morfologia Matematica");
		
		String[] metodos = {"Dilatação", "Erosão", "Fechamento", "Abertura", "Outline"};
		
		
		interfaceGrafica.addRadioButtonGroup("Opções", metodos, 5, 1, null);
		
		
		interfaceGrafica.showDialog();

		if(interfaceGrafica.wasOKed()) {
			
			String opcao_radio = interfaceGrafica.getNextRadioButton();
			
			switch(opcao_radio) {
				case "Dilatação": 
				{
					aplica_dilatacao(processor, auxiliar, elemento_estruturante);
					break;
				}
				case "Erosão":
				{
					aplica_erosao(processor, auxiliar, elemento_estruturante);
					break;
				}
				case "Fechamento":
				{
					aplica_erosao(processor, auxiliar, elemento_estruturante);
					aplica_dilatacao(processor, auxiliar, elemento_estruturante);
					
					break;
				}
				case "Abertura": 
				{
					aplica_dilatacao(processor, auxiliar, elemento_estruturante);
					aplica_erosao(processor, auxiliar, elemento_estruturante);
					break;
				}
					
				case "Outline":
				{
					aplica_erosao(processor, auxiliar, elemento_estruturante);
					outline(processor, auxiliar, elemento_estruturante);
					
					break;
				}
			}
			
			imagem.setProcessor(auxiliar);
			imagem.updateAndDraw();
		}
	}

	public void outline(ImageProcessor processor, ImageProcessor auxiliar, int[][] elemento_estruturante) {
		
		int largura = processor.getWidth(),
			altura = processor.getHeight();
		
		
		for(int i = 1; i < largura - 1; i++)
		{
			for(int j = 1; j < altura - 1; j++)
			{
				int pixel1 = processor.getPixel(i, j);
				int pixel2 = auxiliar.getPixel(i, j);
				
				if(pixel1 == pixel2)
					auxiliar.putPixel(i, j, 0);
				else
					auxiliar.putPixel(i, j, 255);
					
			}
		}
	}

	public void aplica_erosao(ImageProcessor processor, ImageProcessor auxiliar, int[][] elemento_estruturante) {
		
		int largura = processor.getWidth(),
				altura = processor.getHeight();
			
		int pixel = 0;
		int pixel_interesse = 255; //definir se meu pixel alvo é preto ou branco. Nesse caso, branco. 
		int pixel_fundo = 255 - pixel_interesse;
		
		auxiliar.set(pixel_fundo); 
	
		for(int i = 1; i < largura-1; i++)
		{	
			for(int j = 1; j < altura-1; j++)
			{
				int pintar = 1;
				
				//verificar se o elemento estruturante está contido numa região de interesse
				for(int x = -1; x <= 1; x++)
				{
					for(int y = -1; y <= 1; y++)
					{
						if(pintar == 1) 
						{
							pixel = processor.getPixel(i + x, j + y);
							
							if(elemento_estruturante[x + 1][y + 1] == 1) 
							{
								if((pixel != pixel_interesse))
									pintar = 0;
							}
						} else break;
					}
					
					if(pintar == 0) break;
				}
				
				if(pintar == 1)
				{
					auxiliar.putPixel(i, j, pixel_interesse);
				}
			}
		}	
	}

	public void aplica_dilatacao(ImageProcessor processor, ImageProcessor auxiliar, int[][] elemento_estruturante) {
	
		int largura = processor.getWidth(),
				altura = processor.getHeight();
			
		int pixel = 0;
		int valor_pixel = 255; //definir se meu pixel alvo é preto ou branco. Nesse caso, branco. 
		
		for(int i = 1; i < largura-1; i++)
		{	
			for(int j = 1; j < altura-1; j++)
			{
				pixel = processor.getPixel(i, j);
				
				if(pixel == valor_pixel) 
				{
					for(int x = - 1; x <= 1; x++) 
					{
						for(int y = -1; y <= 1; y++)
						{
							if(elemento_estruturante[x + 1][y + 1] == 1) 
							{
								auxiliar.set(i + x, j + y, valor_pixel);
							}	
						}
					}	
				}			
			}	
		}
		
	}
}
