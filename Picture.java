import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 *
 * @author Sreevatsa Pervela
 * @since 2/12/2024
 */
public class Picture extends SimplePicture
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments
     */
    public Picture()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor
         */
        super();
    }

    /**
     * Constructor that takes a file name and creates the picture
     *
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     *
     * @param height the height of the desired picture
     * @param width  the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width, height);
    }

    /**
     * Constructor that takes a picture and creates a
     * copy of that picture
     *
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     *
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     *
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() +
                " height " + getHeight()
                + " width " + getWidth();
        return output;

    }

    /**
     * 4A: A5 : Method to set the blue to 0
     */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }

    /**
     * 4A: A6 : Method to negate pixel colors
     */
    public void negate()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            }
        }
    }

    /**
     * 4A: A7 : Method to set the average color
     */
    public void grayscale()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                int avg = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3;
                pixelObj.setRed(avg);
                pixelObj.setGreen(avg);
                pixelObj.setBlue(avg);
            }
        }
    }


    /**
     * Method that mirrors the picture around a
     * vertical mirror in the center of the picture
     * from left to right
     */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /**
     * Mirror just part of a picture of a temple
     */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];
                rightPixel = pixels[row]
                        [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /**
     * copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     *
     * @param fromPic  the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic,
                     int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow;
             fromRow < fromPixels.length &&
                     toRow < toPixels.length;
             fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol;
                 fromCol < fromPixels[0].length &&
                         toCol < toPixels[0].length;
                 fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }
    }

    /**
     * Method to create a collage of several pictures
     */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1, 0, 0);
        this.copy(flower2, 100, 0);
        this.copy(flower1, 200, 0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue, 300, 0);
        this.copy(flower1, 400, 0);
        this.copy(flower2, 500, 0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }


    /**
     * Method to show large changes in color
     *
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0;
                 col < pixels[0].length - 1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) >
                        edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }


    /* Main method for testing - each class in Java can have a main
     * method
     */
    public static void main(String[] args)
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }
    //*****************************NEW METHODS*********************************

    public void keepOnlyBlue()
    {

        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setRed(0);
                pixelObj.setGreen(0);
            }

        }
    }

    /**
     * To pixelate by dividing area into size x size.
     *
     * @param size Side length of square area to pixelate.
     */
    public void pixelate(int size)
    {
        Pixel[][] pixels = this.getPixels2D();

        // for every row
        for (int i = 0; i < pixels.length; i++)
        {
            // for every column, block by block
            for (int j = 0; j < pixels[0].length; j += size)
            {
                int avgR = 0;
                int avgG = 0;
                int avgB = 0;

                // calculate averages for next block
                for (int k = j; k < j + size; k++)
                {
                    avgR += pixels[i][k].getRed();
                    avgG += pixels[i][k].getGreen();
                    avgB += pixels[i][k].getBlue();
                }

                // change colors for next block using averages
                for (int k = j; k < j + size; k++)
                {
                    pixels[i][k].setRed(avgR / size);
                    pixels[i][k].setGreen(avgG / size);
                    pixels[i][k].setBlue(avgB / size);
                }
            }
        }
    }

    /**
     * Method that blurs the picture
     *
     * @param size Blur size, greater is more blur
     * @return Blurred picture
     */
    public Picture blur(int size)
    {
        Pixel[][] pixels = this.getPixels2D();

        Picture result = new Picture(this);
        Pixel[][] resultPixels = result.getPixels2D();

        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < pixels[0].length; j++)
            {
                int avgR = 0;
                int avgG = 0;
                int avgB = 0;
                int count = 1;

                for (int k = i - size / 2; k < i + size / 2; k++)
                {
                    if (k >= 0 && k < pixels.length)
                    {
                        for (int m = j - size / 2; m < j + size / 2; m++)
                        {

                            if (m >= 0 && m < pixels[0].length)
                            {
                                avgR += pixels[k][m].getRed();
                                avgG += pixels[k][m].getGreen();
                                avgB += pixels[k][m].getBlue();
                                count++;
                            }
                        }
                    }
                }

                resultPixels[i][j].setColor(new Color(avgR / count, avgG / count, avgB / count));
            }
        }
        return result;
    }

    /**
     * Method that enhances a picture by getting average Color around
     * a pixel then applies the following formula:
     * <p>
     * pixelColor <- 2 * currentValue - averageValue
     * <p>
     * size is the area to sample for blur.
     *
     * @param size Larger means more area to average around pixel
     *             and longer compute time.
     * @return enhanced picture
     */
    public Picture enhance(int size)
    {
        Pixel[][] pixels = this.getPixels2D();

        Picture result = new Picture(this);
        Pixel[][] resultPixels = result.getPixels2D();

        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < pixels[0].length; j++)
            {
                int redTotal = 0;
                int greenTotal = 0;
                int blueTotal = 0;
                int count = 1;

                for (int r = i - size / 2; r < i + size / 2; r++)
                {
                    if (r >= 0 && r < pixels.length)
                    {
                        for (int c = j - size / 2; c < j + size / 2; c++)
                        {
                            if (c >= 0 && c < pixels[0].length)
                            {
                                redTotal += pixels[r][c].getRed();
                                greenTotal += pixels[r][c].getGreen();
                                blueTotal += pixels[r][c].getBlue();
                                count++;
                            }
                        }
                    }
                }

                Pixel pixel = pixels[i][j];
                resultPixels[i][j].setRed(2 * pixel.getRed() - redTotal / count);
                resultPixels[i][j].setGreen(2 * pixel.getGreen() - greenTotal / count);
                resultPixels[i][j].setBlue(2 * pixel.getBlue() - blueTotal / count);
            }
        }
        return result;
    }

    /**
     * Method that swaps pixels between left and righrt
     *
     * @return swapped picture
     */
    public Picture swapLeftRight()
    {
        Pixel[][] pixels = this.getPixels2D();
        Picture result = new Picture(this);
        Pixel[][] resultPixels = result.getPixels2D();

        int width = pixels[0].length;
        int mid = width / 2;

        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < mid; j++)
            {
                Pixel temp = pixels[i][j];
                resultPixels[i][j].setColor(pixels[i][width - mid + j].getColor());
                resultPixels[i][width - mid + j].setColor(temp.getColor());
            }
        }
        return result;
    }

    /**
     * Jagged picture using stair steps.
     *
     * @param shiftCount The number of pixels to shift to the right
     * @param steps      The number of steps
     * @return The picture with pixels shifted in stair steps
     */
    public Picture stairStep(int shiftCount, int steps)
    {
        Picture result = new Picture(this);
        Pixel[][] resultPixels = result.getPixels2D();
        Pixel[][] pixels = this.getPixels2D();

        int totalRows = pixels.length;
        int rowsPerStep = totalRows / steps;
        int shift = shiftCount;

        int count = 0;

        for (int i = 0; i < pixels.length; i++)
        {
            if (i % rowsPerStep == 0 && count < steps)
            {
                count++;
                shift = count * shiftCount;
            }
            for (int j = 0; j < pixels[0].length; j++)
            {
                if (j + shift < pixels[0].length)
                {
                    resultPixels[i][j + shift].setColor(pixels[i][j].getColor());

                }
                else
                {
                    resultPixels[i][j + shift - pixels[0].length].setColor(pixels[i][j].getColor());
                }
            }
        }

        return result;
    }


    /**
     * liquify effect which distorts a picture as you drag the mouse across it.
     * In this activity, we will distort the horizontal center of the picture by shifting pixels horizontally.
     *
     * @param maxHeight Max height (shift) of curve in pixels
     * @return Liquified picture
     */
    public Picture liquify(int maxHeight)
    {
        Picture result = new Picture(this);
        Pixel[][] resultPixels = result.getPixels2D();
        Pixel[][] pixels = this.getPixels2D();

        int height = pixels.length;
        int width = pixels[0].length;
        double x0 = height / 2.0;
        double bellWidth = 20;

        for (int col = 0; col < width; col++)
        {
            for (int row = 0; row < height; row++)
            {
                double exponent = Math.pow(row - x0, 2) / (2.0 * Math.pow(bellWidth, 2));
                int rightShift = (int) (maxHeight * Math.exp(-exponent));
                if (col + rightShift < pixels[0].length)
                {
                    resultPixels[row][col + rightShift].setColor(pixels[row][col].getColor());
                }
                else
                {
                    // 80 + 40 == 120; 120 - 100
                    resultPixels[row][col + rightShift - pixels[0].length].setColor(pixels[row][col].getColor());
                }
            }
        }
        return result;
    }


    /**
     * wavy is another liquify effect to create oscillating distortions in a picture.
     * formula : g( x) = Asin(2π fx +ϑ)
     *
     * @param amplitude the maximum shift of pixels
     * @return Wavy picture
     */
    public Picture wavy(int amplitude)
    {
        Picture result = new Picture(this);
        Pixel[][] resultPixels = result.getPixels2D();
        Pixel[][] pixels = this.getPixels2D();

        double frequency = 0.5;
        double phaseShift = 0;

        //formula : g( x) = Asin(2π fx +ϑ)
        for (int i = 0; i < pixels.length; i++)
        {
            int shift = (int) (amplitude * Math.sin(2 * Math.PI * frequency * (i / 10.0) + phaseShift));

            if (shift > 0)
            {
                //right shift
                for (int j = 0; j < pixels[0].length; j++)
                {
                    if (j + shift < pixels[0].length)
                    {
                        resultPixels[i][j + shift].setColor(pixels[i][j].getColor());
                    }
                    else
                    {
                        // 80 + 40 == 120; 120 - 100
                        resultPixels[i][j + shift - pixels[0].length].setColor(pixels[i][j].getColor());
                    }
                }
            }
            else if (shift < 0)
            {
                //left shift
                for (int j = 0; j < pixels[0].length; j++)
                {
                    if (j + shift > 0)
                    {
                        resultPixels[i][j + shift].setColor(pixels[i][j].getColor());
                    }
                    else
                    {
                        // 20 - 30 == -10; 100 - 10 == 90
                        resultPixels[i][pixels[0].length + (j + shift) - 1].setColor(pixels[i][j].getColor());
                    }
                }
            }
        }
        return result;
    }

    
}

