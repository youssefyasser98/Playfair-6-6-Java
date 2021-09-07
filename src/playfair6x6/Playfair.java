/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playfair6x6;

/**
 *
 * @author ARWA
 */

class basic{
    //allCharacter is a string have all alphabetic and number characters
    String allCharacter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    //method checks if the input matches the characters in the string allCharacter
    boolean indexOfChar(char c){
        for (int i = 0; i < allCharacter.length(); i++){
            if (allCharacter.charAt(i) == c)
                return true;
        }
        // illegal character
        return false;
    }
}

 class Playfair {
    basic b = new basic();
    char keyMatrix[][] = new char[6][6];
    boolean repeat(char c){
        
        if (!b.indexOfChar(c))
            return true;
       
        for (int i = 0; i < keyMatrix.length; i++){
            
            for (int j = 0; j <keyMatrix[i].length; j++){
                if (keyMatrix[i][j] == c )
                    return true;
            }
        }
        
        return false;
    }
    
    void insertKey(String key){
        key = key.toUpperCase();
//        key = key.replaceAll("J", "I");
          key = key.replaceAll(" ", "");
        
        //a = row, b = coloumn
        int a = 0;
        int b = 0;   
        
        for (int k = 0; k < key.length(); k++){
            if (!repeat(key.charAt(k))){
                keyMatrix[a][b++] = key.charAt(k);
                
                //when reach to last coloumn set row++ and coloumn = 0 
                if (b > 5){
                    b = 0;
                    a++;
                }
            }
        }
        
        //p is the first character 'a' 
        char p = 'A';
        
        //5 is the number of rows
        while (a < 6 ){
            // 5 is number of columns
            while (b < 6){
                //checks if the character 'a' is not exist in the matrix
                if(!repeat(p))
                    // if not exit put it in the matrix
                    keyMatrix[a][b++] = p;
                
                //move to the next character
                p++;
            }
            b = 0;
            a++;
        }
        
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> START OF MATRIX <<<<<<<<<<<<<<<<<<<<<");
        for (int i = 0; i < 6; i++){
            System.out.println();
            for (int j = 0; j < 6; j++)
                System.out.print("\t" + keyMatrix[i][j]);
        }
        System.out.println("\n>>>>>>>>>>>>>>>>>>>> END OF MATRIX <<<<<<<<<<<<<<<<<<<<<");
    }
    
    
    // we make two methods to return row and column posotion because onw method can not return two values
    int rowPosition (char c){
        for (int i = 0; i < keyMatrix.length; i++){
            for (int j = 0; j < keyMatrix[i].length; j++){
                if (keyMatrix[i][j] == c)
                    // return row posotion
                    return i;
            }
        }
        // if the character doesn't exist and it is imposiible 
        return -1;
    }
    
    int columnPosition (char c){
        for (int i = 0; i < keyMatrix.length; i++){
            for (int j = 0; j < keyMatrix[i].length; j++){
                if (keyMatrix[i][j] == c)
                    // return column posotion
                    return j;
            }
        }
        // if the character doesn't exist and it is imposiible 
        return -1;
    }
    
    
    String encryptChar(String plain){
        plain = plain.toUpperCase();
        
        int r1, r2, c1, c2;
        
        String cipherChar = "";
        char a = plain.charAt(0);
        char b = plain.charAt(1);
        
        r1 = rowPosition(a);
        c1 = columnPosition(a);
        r2 = rowPosition(b);
        c2 = columnPosition(b);
        
        if (c1 == c2 ){
            ++r1;
            ++r2;
            
            // if we reached the last row,,, return to the first row
            if (r1 > 5)
                r1 = 0;
            if (r2 > 5)
                r2 = 0;
            
            //add to cipher text 
            cipherChar += keyMatrix[r1][c2];
            cipherChar += keyMatrix[r2][c1];
        }
        else  if (r1 == r2){
            ++c1;
            ++c2;
            if (c1 > 5)
                c1 = 0;
            if (c2 > 5)
                c2 = 0;
            
            //add to cipher text
            cipherChar += keyMatrix[r1][c1];
            cipherChar += keyMatrix[r2][c2];
        }
        else{
            cipherChar += keyMatrix[r1][c2];
            cipherChar += keyMatrix[r2][c1];
        }
        return cipherChar;
    }
    
    
    
    
    String dencryptChar(String cipher){
        cipher = cipher.toUpperCase();
        
        int r1, r2, c1, c2;
        
        String plainChar = "";
        char a = cipher.charAt(0);
        char b = cipher.charAt(1);
        
        r1 = rowPosition(a);
        c1 = columnPosition(a);
        r2 = rowPosition(b);
        c2 = columnPosition(b);
        
        if (c1 == c2 ){
            --r1;
            --r2;
            
            // if we reached the last row,,, return to the first row
            if (r1 < 0)
                r1 = 5;
            if (r2 < 0)
                r2 = 5;
            
            //add to cipher text 
            plainChar += keyMatrix[r1][c2];
            plainChar += keyMatrix[r2][c1];
        }
        else  if (r1 == r2){
            --c1;
            --c2;
            if (c1 < 0)
                c1 = 5;
            if (c2 < 0)
                c2 = 5;
            
            //add to cipher text
            plainChar += keyMatrix[r1][c1];
            plainChar += keyMatrix[r2][c2];
        }
        else{
            plainChar += keyMatrix[r1][c2];
            plainChar += keyMatrix[r2][c1];
        }
        return plainChar;
    }
    
    
    
    String encrypt(String plainText, String key){
        insertKey(key);
        String cipherText = "";
       // plainText = plainText.replaceAll("J", "I");
        plainText = plainText.replaceAll(" ", "");
        plainText = plainText.toUpperCase();
        
        int plainTxtLength = plainText.length();
        
        //checks if the length of plain text is odd to add x in the last
        if (plainTxtLength / 2 != 0){
            plainText += "x";
            plainTxtLength++;
        }
        
        for (int i = 0; i < plainTxtLength -1; i+=2){
            
            //substring to take only two characters of the plain text
            cipherText += encryptChar(plainText.substring(i, i+2));
            
        }
        
        return cipherText;
    }
    
    
    
    String dencrypt(String cipherText, String key){
        //insertKey(key);
        String plainText = "";
        //cipherText = cipherText.replaceAll("j", "i");
        cipherText = cipherText.replaceAll(" ", "");
        cipherText = cipherText.toUpperCase();
        
        int cipherTxtLength = cipherText.length();
   
        for (int i = 0; i < cipherTxtLength -1; i+=2){
            
            //substring to take only two characters of the plain text
            plainText += dencryptChar(cipherText.substring(i, i+2));
            
        }
        
        return plainText;
    }
}
