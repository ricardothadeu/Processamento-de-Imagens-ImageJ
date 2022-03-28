import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.ImagePlus;
import ij.IJ;

public class Converter_RGB_HSV implements PlugIn {

	
	public void run(String arg) {
		
		rgb_to_hsv();
		
	}
	
	public void rgb_to_hsv() {
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processor = imagem.getProcessor();
		
		int largura = processor.getWidth(),
				altura = processor.getHeight();
		
		ImagePlus imagem_h = IJ.createImage("hue", "8-bit", largura, altura, 0);
		ImagePlus imagem_s = IJ.createImage("saturation", "8-bit", largura, altura, 0);
		ImagePlus imagem_v = IJ.createImage("value", "8-bit", largura, altura, 0);
		
		ImageProcessor processor_h = imagem_h.getProcessor();
		ImageProcessor processor_s = imagem_s.getProcessor();
		ImageProcessor processor_v = imagem_v.getProcessor();
		
		double MAX, MIN;
		int[] RGB = {0,0,0};
		
		for(int i = 0; i < largura; i++)
		{
			for(int j = 0; j < altura; j++)
			{
				processor.getPixel(i, j, RGB);
				
				double red = (double)RGB[0]/255.0,
					   green = (double)RGB[1]/255.0,
					   blue = (double)RGB[2]/255.0;
				
				double h = 0, s = 0, v = 0;
				
				MAX = Math.max(red, Math.max(green, blue));
				
				
				MIN = Math.min(red, Math.min(green, blue));
				
				if(MAX == MIN)
					h = 0;
				
				if(MAX == red)
				{
					if(green >= blue)
						h = 60 * (green - blue)/(MAX - MIN);
					else
						h = (60 * (green - blue)/(MAX - MIN) + 360);
				}
				
				if(MAX == green)
				{
					h = 60 * (blue - red)/(MAX - MIN) + 120;
				}
				
				if(MAX == blue)
				{
					h = 60 * (red - green)/(MAX - MIN) + 240;
				}
			
				if(MAX == 0)
					s = 0;
				else
					s = ((MAX - MIN)/MAX) * 255;
		
		 		v = MAX * 255;	
				
				processor_h.set(i, j, (int)h);
				processor_s.set(i, j, (int)s);
				processor_v.set(i, j, (int)v);		
			}
		}
		
		imagem_h.show();
		imagem_s.show();
		imagem_v.show();
		
	}

}
