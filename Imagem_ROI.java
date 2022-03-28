import ij.plugin.PlugIn;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.IJ;

public class Imagem_ROI implements PlugIn {

	public void run(String arg) {
		
		RoiManager roi_manager = new RoiManager();
		
		ImagePlus imagem_original = IJ.getImage();
		ImageProcessor processor = imagem_original.getProcessor();
		
		ImagePlus imagem_binaria = imagem_original.duplicate();
		ImageProcessor processor_binaria = imagem_binaria.getProcessor();
			
		imagem_binaria.show();
		
		IJ.run("Make Binary");
	
		
		ParticleAnalyzer.setRoiManager(roi_manager);
		
		IJ.run(imagem_binaria, "Analyze Particles...", "add");
		Roi[] vetor_roi = roi_manager.getRoisAsArray();
				
		for(int i = 0; i < vetor_roi.length; i++)
		{
			//pegando cada ROI da imagem binária e dando um set desse ROI na imagem original
			imagem_original.setRoi(vetor_roi[i]);
			
			//criando uma nova imagem, que vai receber o resultado do crop 
			ImagePlus imagem_cropada = new ImagePlus();
			
			//cortando a imagem com base no ROI atual
			imagem_cropada = imagem_original.crop();
			
			IJ.save(imagem_cropada, "/home/ricardo/Downloads/PDI/Imagens/" + "Sprite  " + i + ".jpg");
		}	
	}

}
