/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import java.util.ArrayList;

/**
 *
 * @author K Sreram
 */
public class MatrixOppr {
  
   private ArrayList<ArrayList <Integer>> matrix;
   private Integer xSize, ySize;
   
   public MatrixOppr (ArrayList<ArrayList <Integer>> pMatrix, Integer pXSize, Integer pYSize) {
       matrix = pMatrix;
       xSize  = pXSize;
       ySize  = pYSize;
   }
   
   public Integer getXSize() {
       return xSize;
   }
   public Integer getYSize() {
       return ySize;
   }
   
   public ArrayList<ArrayList <Integer>> getMatrix () {
       return new ArrayList<ArrayList <Integer>>(matrix);
   }
   
   public Integer getElement (int y, int x) {
       return matrix.get(y).get(x);
   }
   
   public MatrixOppr multiply (MatrixOppr argMatrix) {
       
       ArrayList <ArrayList <Integer> > result = new
               ArrayList <> ();
    
       if (!getXSize ().equals(argMatrix.getYSize()))  {
           /// throw exception
           return null;
       }
       /// note: xSize and matrix.xSize are equals
       Integer sumSize  = this.getXSize();
       Integer newYSize = this.getXSize();
       Integer newXSize = argMatrix.getXSize();
       
       for (int i = 0; i < newYSize; i++) {
           result.add(new ArrayList <> ());
           for (int j = 0; j < newXSize; j++) {
               int sum = 0;
               
               for (int k = 0; k  < sumSize; k++ ) {
                   sum += this.matrix.get(i).get(k) * argMatrix.getElement(k, j);
               }
               result.get(i).add(sum);
           }
       }
       return new MatrixOppr (result, newXSize, newYSize);
   }
   
    
}
