import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;

public class Aplicar_threshold implements PlugIn, DialogListener {

	ImagePlus imagem = IJ.getImage();
	ImageProcessor processor = imagem.getProcessor();
	
	public void run(String arg) {
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("Threshold");
		interfaceGrafica.addDialogListener(this);
		
		interfaceGrafica.addSlider("Definir limiar", 0, 255, 128, 1);
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			imagem.setProcessor(processor);
		}
	}

	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		threshold(processor, imagem, interfaceGrafica.getNextNumber());
        return true;
    }
	
	public void threshold (ImageProcessor processor, ImagePlus imagem, double limiar) {
		
		ImageProcessor processor_auxiliar = processor.duplicate();
		
		int altura = processor_auxiliar.getHeight();
		int largura = processor_auxiliar.getWidth();
		int pixel;
		
		for(int i = 0; i < largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{
				pixel = processor_auxiliar.get(i, j);
				
				if(pixel < limiar)
					pixel = 0;
				else
					pixel = 255;
				
				processor_auxiliar.set(i, j, pixel);
			}
		}
		
		imagem.setProcessor(processor_auxiliar);
		imagem.updateAndDraw();

	}

	
}
	


