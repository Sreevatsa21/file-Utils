import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu, Sreevatsa Pervela
 * @since 2/1/2024
 */
public class Picture extends SimplePicture  {
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture() {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels) {
      for(Pixel pixelObj : rowArray)
        pixelObj.setBlue(0);
    }
  }
  
  /** Method to set the red and green to 0 */
  public void keepOnlyBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels) {
      for(Pixel pixelObj : rowArray) {
        pixelObj.setRed(0);
        pixelObj.setGreen(0); 
      }
    }
  }
  
  public void negate() {
	  Pixel[][] pixels = this.getPixels2D();
	  for(Pixel[] rowArray : pixels) {
		  for(Pixel pixelObj : rowArray) {
				pixelObj.setRed(255 - pixelObj.getRed());
				pixelObj.setGreen(255 - pixelObj.getGreen());
				pixelObj.setBlue(255 - pixelObj.getBlue());
		  }
	  }
  }
  
    public void grayscale() {
		  Pixel[][] pixels = this.getPixels2D();
		  for(Pixel[] rowArray : pixels) {
			  for(Pixel pixelObj : rowArray) {
					int avg = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue())/3; 
					pixelObj.setRed(avg);
					pixelObj.setGreen(avg);
			  	pixelObj.setBlue(avg);
			  }
		  }
  }
  
	/** To pixelate by dividing area into size x size.
	* @param size Side length of square area to pixelate.
	*/
	public void pixelate(int size) {
		Pixel[][] pixels = this.getPixels2D();
		for(int yVal = 0; yVal < pixels.length; yVal += size) {
			int yMax = yVal + size;
			if(yMax >= pixels.length)
				yMax = pixels.length - 1; 
			for(int xVal = 0; xVal < pixels[0].length; xVal += size) {
				int xMax = xVal + size;
				if(xMax >= pixels[0].length)
					xMax = pixels[0].length - 1; 
				int rSum = 0; 
				int gSum = 0; 
				int bSum = 0; 
				int num = 0; 
				
				for(int xMin = xVal; xMin <= xMax; xMin++){
					for(int yMin = yVal; yMin <= yMax; yMin++){
						rSum += pixels[yMin][xMin].getRed(); 
						gSum += pixels[yMin][xMin].getGreen();
						bSum += pixels[yMin][xMin].getBlue();
						num++; 
					}
				}
				
				for(int xMin = xVal; xMin <= xMax; xMin++){
					for(int yMin = yVal; yMin <= yMax; yMin++){
						pixels[yMin][xMin].setRed(rSum/num);
						pixels[yMin][xMin].setGreen(gSum/num);
						pixels[yMin][xMin].setBlue(bSum/num);
					}
				}
			}
		}
	}
	
	/** Method that blurs the picture
	* @param size Blur size, greater is more blur
	* @return Blurred picture
	*/
	public Picture blur(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		for (int yVal = 0; yVal < pixels.length; yVal++){
			int yMin = Math.max(0, yVal - (size/2)); 
			int yMax = Math.min(pixels.length - 1, yVal + (size/2)); 
			for (int xVal = 0; xVal < pixels[0].length; xVal++){
				int xMin = Math.max(0, xVal - (size/2)); 
				int xMax = Math.min(pixels[0].length - 1, xVal + (size/2)); 
				
				int rSum = 0; 
				int gSum = 0; 
				int bSum = 0; 
				int num = (yMax - yMin)*(xMax - xMin); 
				for (int x = xMin; x <= xMax; x++){
					for (int y = yMin; y <= yMax; y++){
						rSum += pixels[y][x].getRed(); 
						gSum += pixels[y][x].getGreen();
						bSum += pixels[y][x].getBlue();
					}
				}
				resultPixels[yVal][xVal].setRed(rSum/num);
				resultPixels[yVal][xVal].setBlue(bSum/num);
				resultPixels[yVal][xVal].setGreen(gSum/num);
			}
		}
		return result; 
	}
	
	/** Method that enhances a picture by getting average Color around
	* a pixel then applies the following formula:
	*
	* pixelColor <- 2 * currentValue - averageValue
	*
	* size is the area to sample for blur.
	*
	* @param size Larger means more area to average around pixel
	* and longer compute time.
	* @return enhanced picture
	*/
	public Picture enhance(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
        for (int yVal = 0; yVal < pixels.length; yVal++){
			int yMin = Math.max(0, yVal - (size/2)); 
			int yMax = Math.min(pixels.length - 1, yVal + (size/2)); 
			for (int xVal = 0; xVal < pixels[0].length; xVal++){
				int xMin = Math.max(0, xVal - (size/2)); 
				int xMax = Math.min(pixels[0].length - 1, xVal + (size/2)); 
				
				int rSum = 0; 
				int gSum = 0; 
				int bSum = 0; 
				int num = (yMax - yMin)*(xMax - xMin); 
				for (int x = xMin; x <= xMax; x++){
					for (int y = yMin; y <= yMax; y++){
						rSum += pixels[y][x].getRed(); 
						gSum += pixels[y][x].getGreen();
						bSum += pixels[y][x].getBlue();
					}
				}
				resultPixels[yVal][xVal].setRed(2 * pixels[yVal][xVal].getRed() - rSum/num);
				resultPixels[yVal][xVal].setBlue(2 * pixels[yVal][xVal].getBlue() - bSum/num);
				resultPixels[yVal][xVal].setGreen(2 * pixels[yVal][xVal].getGreen() - gSum/num);
			}
		}
		return result; 
	}
	/**
	 * shifts the entire picture to the right
	 * @param int percent	the amount shifted to the right
	 * @return Picture	returns the shifted picture
	 */
	public Picture shiftRight(int percent){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		int move = pixels[0].length * percent / 100; 
		for (int yVal = 0; yVal < pixels.length; yVal++){
			for (int xVal = 0; xVal < pixels[0].length; xVal++){
				if (xVal + move < pixels[0].length){
					resultPixels[yVal][xVal + move].setRed(pixels[yVal][xVal].getRed());
					resultPixels[yVal][xVal + move].setBlue(pixels[yVal][xVal].getBlue());
					resultPixels[yVal][xVal + move].setGreen(pixels[yVal][xVal].getGreen());
				}
				else{
					resultPixels[yVal][xVal + move - pixels[0].length].setRed(pixels[yVal][xVal].getRed());
					resultPixels[yVal][xVal + move - pixels[0].length].setBlue(pixels[yVal][xVal].getBlue());
					resultPixels[yVal][xVal + move - pixels[0].length].setGreen(pixels[yVal][xVal].getGreen());
				}
			}
		}
		return result; 
	}
	/**
	 * Creates a jagged picture with stair steps
	 * @param int shiftCount is	the	number of pixels to shift right at each stair step.
	 * @param int steps the number of steps
	 * @return Picture	returns the jagged picture
	 */
	public Picture stairStep(int shiftCount, int steps){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		int move = 0; 
		int stepSize = pixels.length / steps; 
		int excess = pixels.length - stepSize * steps; 
		int extraIndex = 0;
		if (excess != 0)
			extraIndex = steps / excess; 
		int nextIndent = stepSize; 
		int step = 1; 
		boolean notAdded = true;
		System.out.println(extraIndex);
		for (int yVal = 0; yVal < pixels.length; yVal++){
			if (yVal == nextIndent){
				System.out.println(yVal + " " + nextIndent + " " + step);
				if (step % extraIndex == 0 && notAdded){
					nextIndent++; 
					notAdded = false;
				}
				else{
					move += shiftCount;
					step++;
					nextIndent += stepSize; 
					notAdded = true; 
				}
			}
			for (int xVal = 0; xVal < pixels[0].length; xVal++){
				if (xVal + move < pixels[0].length){
					resultPixels[yVal][xVal + move].setRed(pixels[yVal][xVal].getRed());
					resultPixels[yVal][xVal + move].setBlue(pixels[yVal][xVal].getBlue());
					resultPixels[yVal][xVal + move].setGreen(pixels[yVal][xVal].getGreen());
				}
				else{
					resultPixels[yVal][xVal + move - pixels[0].length].setRed(pixels[yVal][xVal].getRed());
					resultPixels[yVal][xVal + move - pixels[0].length].setBlue(pixels[yVal][xVal].getBlue());
					resultPixels[yVal][xVal + move - pixels[0].length].setGreen(pixels[yVal][xVal].getGreen());
				}
			}
		}
		return result; 
	}
	/**
	 * turns the picture 90 degrees.
	 * @return picture returns the rotated picture
	 */
	public Picture turn90(){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels[0].length, pixels.length);
		Pixel[][] resultPixels = result.getPixels2D();
		for (int yVal = 0; yVal < pixels.length; yVal++){
			for (int xVal = 0; xVal < pixels[0].length; xVal++){
				resultPixels[xVal][resultPixels[0].length - yVal - 1].setRed(pixels[yVal][xVal].getRed());
				resultPixels[xVal][resultPixels[0].length - yVal - 1].setBlue(pixels[yVal][xVal].getBlue());
				resultPixels[xVal][resultPixels[0].length - yVal - 1].setGreen(pixels[yVal][xVal].getGreen());
			}
		}
		return result; 
	}
	/**
	 * 
	 * Zooms in to the upper left corner picture 
	 * 
	 * @return picture returns the top left corner
	 */
	public Picture zoomUpperLeft(){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		for (int yVal = 0; yVal < (pixels.length - 1) / 2; yVal++){
			for (int xVal = 0; xVal < (pixels[0].length - 1)/ 2; xVal++){
				for (int i = 0; i <= 1; i++){
					for (int j = 0; j <= 1; j++){
					System.out.println(yVal * 2 + i);
					resultPixels[yVal * 2 + i][xVal * 2 + j].setRed(pixels[yVal][xVal].getRed());
					resultPixels[yVal * 2 + i][xVal * 2 + j].setBlue(pixels[yVal][xVal].getBlue());
					resultPixels[yVal * 2 + i][xVal * 2 + j].setGreen(pixels[yVal][xVal].getGreen());
				}
				}
			}
		}
		return result; 
	}
	  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
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
  
  /** Mirror just part of a picture of a temple */
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
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
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

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
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
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > edgeDist)
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
  
} // this } is the end of class Picture, put all new methods before this
