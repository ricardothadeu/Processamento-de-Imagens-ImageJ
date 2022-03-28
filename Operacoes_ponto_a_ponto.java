import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;

public class Operacoes_ponto_a_ponto implements PlugIn, DialogListener {

	ImagePlus imagem = IJ.getImage();
	ImageProcessor processor = imagem.getProcessor();

	public void run(String arg) {
		apresentarInterfaceGrafica();
	}

	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("Operações Ponto a Ponto");
		interfaceGrafica.addDialogListener(this);

		interfaceGrafica.addSlider("Brilho", -255, 255, 0, 1);
		interfaceGrafica.addSlider("Contraste", -255, 255, 0, 1);
		interfaceGrafica.addSlider("Solarização", 0, 255, 255, 1);
		interfaceGrafica.addSlider("Dessaturação", 0, 1, 0.5, 0.1);
		interfaceGrafica.showDialog();

		if (interfaceGrafica.wasCanceled()) {
			imagem.setProcessor(processor);
		}
	}

	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled())
			return false;

		ImageProcessor processor_auxiliar = processor.duplicate();

		int nivel_brilho = (int) interfaceGrafica.getNextNumber(),
				nivel_contraste = (int) interfaceGrafica.getNextNumber(),
				nivel_solarizacao = (int) interfaceGrafica.getNextNumber();

		double nivel_dessaturacao = interfaceGrafica.getNextNumber();

		alterar_brilho(processor_auxiliar, nivel_brilho);
		alterar_contraste(processor_auxiliar, nivel_contraste);
		alterar_solarizacao(processor_auxiliar, nivel_solarizacao);
		alterar_dessaturacao(processor_auxiliar, nivel_dessaturacao);

		imagem.setProcessor(processor_auxiliar);
		imagem.updateAndDraw();
	
		return true;
	}

	private void alterar_brilho(ImageProcessor processor_auxiliar, int nivel_brilho) {

		int altura = processor_auxiliar.getHeight(), largura = processor_auxiliar.getWidth();
	
		int[] valorPixel = { 0, 0, 0 };

		for (int i = 0; i < largura; i++) {
			for (int j = 0; j < altura; j++) {

				processor_auxiliar.getPixel(i, j, valorPixel);
				
				valorPixel[0] += nivel_brilho;
				valorPixel[1] += nivel_brilho;
				valorPixel[2] += nivel_brilho;

				for (int k = 0; k < 3; k++) {
					if (valorPixel[k] > 255)
						valorPixel[k] = 255;
					if (valorPixel[k] < 0)
						valorPixel[k] = 0;
				}

				processor_auxiliar.putPixel(i, j, valorPixel);
			}
		}

	}

	private void alterar_contraste(ImageProcessor processor_auxiliar, int nivel_contraste) {

		int altura = processor_auxiliar.getHeight(),
			largura = processor_auxiliar.getWidth();

		int[] valorPixel = { 0, 0, 0 };

		double fator_contraste = (double)(259 * (nivel_contraste + 255)) / (255 * (259 - nivel_contraste));

		for (int i = 0; i < largura; i++) {
			for (int j = 0; j < altura; j++) {

				processor_auxiliar.getPixel(i, j, valorPixel);

				for (int k = 0; k < 3; k++) {

					valorPixel[k] = (int)(fator_contraste * (valorPixel[k] - 128) + 128);

					if (valorPixel[k] > 255)
						valorPixel[k] = 255;
					if (valorPixel[k] < 0)
						valorPixel[k] = 0;
				}

				processor_auxiliar.putPixel(i, j, valorPixel);
			}
		}

	}
	private void alterar_solarizacao(ImageProcessor processor_auxiliar, int nivel_solarizacao) {

		// vou inverter os pixels acima do limiar

		int altura = processor_auxiliar.getHeight(), largura = processor_auxiliar.getWidth();

		int[] valorPixel = { 0, 0, 0 };

		for (int i = 0; i < largura; i++) {
			for (int j = 0; j < altura; j++) {
				processor_auxiliar.getPixel(i, j, valorPixel);

				for (int k = 0; k < 3; k++) {

					if (valorPixel[k] > nivel_solarizacao) {
						//IJ.log("Pixel: " + valorPixel[k]);
						valorPixel[k] = 255 - valorPixel[k];
						 
						
						
						if (valorPixel[k] > 255)
							valorPixel[k] = 255;
						if (valorPixel[k] < 0)
							valorPixel[k] = 0;
					}
				}

				processor_auxiliar.putPixel(i, j, valorPixel);
			}
		
		}
	}
	
	private void alterar_dessaturacao (ImageProcessor processor_auxiliar, double nivel_dessaturacao) {
		
		int altura = processor_auxiliar.getHeight(), 
			largura = processor_auxiliar.getWidth();

		int[] valorPixel = { 0, 0, 0 };
		double gray;

		for(int i = 0; i < largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{
				processor_auxiliar.getPixel(i, j, valorPixel);
				
				gray = (valorPixel[0] * 0.2125 + valorPixel[1] * 0.7154 + valorPixel[2] * 0.072);
				
				valorPixel[0] = (int)(gray + nivel_dessaturacao * (valorPixel[0] - gray));
				valorPixel[1] = (int)(gray + nivel_dessaturacao * (valorPixel[1] - gray));
				valorPixel[2] = (int)(gray + nivel_dessaturacao * (valorPixel[2] - gray));
				
				processor_auxiliar.putPixel(i, j, valorPixel);
			}
		}
	}
		
}

