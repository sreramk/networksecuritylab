/*
 * copyright (c) 2018 K Sreram, All rights reserved.
 */
package matrix;

import java.util.ArrayList;

/**
 *
 * @author K Sreram
 */
public class MatrixOppr {
  
   private final ArrayList<ArrayList <Integer>> matrix;
   private final Integer xSize, ySize;
   
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
       return new ArrayList<>(matrix);
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
   
   
   public MatrixOppr minor (int x, int y) {
       
       
       Integer newXSize = this.getXSize() -1;
       Integer newYSize = this.getYSize() -1;
       
       ArrayList<ArrayList<Integer>> result = new ArrayList<>();
       
       for (int i = 0, k = 0; i < this.getXSize(); i++ ) {
           
           if (i == y) continue;
           
           result.add(new ArrayList<>());
           for (int j = 0; j < this.getYSize(); j++) {
               if (j != x ) {
                   result.get(k).add(this.matrix.get(i).get(j));
               }
           }
           k++;
       }
       return new MatrixOppr (result, newXSize, newYSize);
   }
   
   public Integer determinant () {
       
       if (    (!this.getXSize().equals(this.getYSize())) || 
               (this.getXSize().equals(0))                ||
               (this.getYSize().equals(0)) ) {
           // throw error
           return null;
       }
       
       if (this.getXSize().compareTo(2) == 0) {
           return (this.matrix.get(1).get(1) * this.matrix.get(0).get(0) - 
                   this.matrix.get(1).get(0) * this.matrix.get(0).get(1));
       }
       
       if (this.getXSize().compareTo(1) == 0){
           return this.matrix.get(0).get(0);
       }
       
       int sign = 1;
       int sum  = 0;
       
       for (int i = 0; i < this.getXSize(); i++) {
           sum += sign * this.minor(i, 0).determinant();
           sign = sign * (-1);
       }
       
       return sum;
   }
   
   public MatrixOppr cofactorMatrix () {
       ArrayList<ArrayList<Integer>> result = new ArrayList<>();
       for (int i = 0; i < this.getYSize(); i++) {
           result.add(new ArrayList<>());
           for (int j = 0; j < this.getXSize(); j++) {
               result.get(i).add(this.cofactorMatrix().determinant());
           }
       }
       return new MatrixOppr (result, this.getXSize(), this.getYSize());
   }
   
   
    
}
