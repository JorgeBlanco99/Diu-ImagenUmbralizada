package imagenUmbralizada;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Jorge
 */
public class ImageViewer extends JPanel {

    private Mat vanillaImage;
    private Mat thresholdImage;
    private BufferedImage displayedImage;

    public ImageViewer() {
        super();
    }

    public void setImage(Mat image) {
        vanillaImage = image.clone();
        thresholdImage = vanillaImage.clone();
        displayedImage = (BufferedImage) HighGui.toBufferedImage(thresholdImage);
        repaint();
    }

    public BufferedImage getImage() {
        return cloneImage(displayedImage);
    }

    private static BufferedImage cloneImage(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void applyThreshold(int threshold) {
        thresholdImage = umbralizar(vanillaImage, threshold);
        displayedImage = (BufferedImage) HighGui.toBufferedImage(thresholdImage);
        repaint();
    }

    private Mat umbralizar(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(),
                imagen_original.cols(),
                CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(),
                imagen_original.cols(),
                CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original,
                imagenGris,
                Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris,
                imagenUmbralizada,
                umbral,
                255,
                Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(displayedImage, 0, 0, null);
    }
}