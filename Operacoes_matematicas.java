 import ij.plugin.PlugIn;
import ij.WindowManager;
import ij.plugin.ImageCalculator;
import ij.IJ;
import ij.ImagePlus;

public class Operacoes_matematicas implements PlugIn {

	public void run(String arg) {
		
		int id[] = WindowManager.getIDList();
		
		ImagePlus imagem1 = WindowManager.getImage(id[0]);
		ImagePlus imagem2 = WindowManager.getImage(id[1]);
		
		String[] operacoes = {"Add", "Subtract", "Multiply", "Divide", "AND", "OR", "XOR",
				"Min", "Max", "Average", "Difference", "Copy", "Transparent-zero"};
		
		for(String operacao : operacoes)
		{
			ImagePlus imagem3 = ImageCalculator.run(imagem1, imagem2, operacao + "create");
			IJ.save(imagem3, "/home/ricardo/Downloads/PDI/Imagens/" + imagem1.getShortTitle() + " " + operacao + " " +
					imagem2.getShortTitle() + ".jpg");
		}
		
		IJ.showMessage("Operações Realizadas! Veja as imagens na pasta destino");
	}
}
