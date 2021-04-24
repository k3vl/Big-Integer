package bigint;

import bigint.BigInteger;
import bigint.DigitNode;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

    /**
     * True if this is a negative integer
     */    
    boolean negative;
    
    /**
     * Number of digits in this integer
     */
    int numDigits;
    
    /**
     * Reference to the first node of this integer's linked list representation
     * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
     * For instance, the integer 235 would be stored as:
     *    5 --> 3  --> 2
     *    
     * Insignificant digits are not stored. So the integer 00235 will be stored as:
     *    5 --> 3 --> 2  (No zeros after the last 2)        
     */
    DigitNode front;
    
    /**
     * Initializes this integer to a positive number with zero digits, in other
     * words this is the 0 (zero) valued integer.
     */
    public BigInteger() {
        negative = false;
        numDigits = 0;
        front = null;
    }
    
    /**
     * Parses an input integer string into a corresponding BigInteger instance.
     * A correctly formatted integer would have an optional sign as the first 
     * character (no sign means positive), and at least one digit character
     * (including zero). 
     * Examples of correct format, with corresponding values
     *      Format     Value
     *       +0            0
     *       -0            0
     *       +123        123
     *       1023       1023
     *       0012         12  
     *       0             0
     *       -123       -123
     *       -001         -1
     *       +000          0
     *       
     * Leading and trailing spaces are ignored. So "  +123  " will still parse 
     * correctly, as +123, after ignoring leading and trailing spaces in the input
     * string.
     * 
     * Spaces between digits are not ignored. So "12  345" will not parse as
     * an integer - the input is incorrectly formatted.
     * 
     * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
     * constructor
     * 
     * @param integer Integer string that is to be parsed
     * @return BigInteger instance that stores the input integer.
     * @throws IllegalArgumentException If input is incorrectly formatted
     */
    public static BigInteger parse(String integer) 
    throws IllegalArgumentException {
        
        /* IMPLEMENT THIS METHOD */
            
        /*
        negative = false;
        numDigits = 0;
        front = null; 
         */
        
        BigInteger list = new BigInteger();
        boolean leadingZero = true;
        
        String temp = integer;
        temp = temp.trim(); 
        int counter = 0;
        
        if(temp.isEmpty()) {
            throw new IllegalArgumentException();
        }
        
        if (temp.charAt(0) == '+') {
            if(temp.length() == 1) {
                throw new IllegalArgumentException();
            }        
            else {
                temp = temp.substring(1,temp.length());
            }
        }
        else if(temp.charAt(0) == '-') {
            if (temp.length() == 1) {
                throw new IllegalArgumentException();
            }
            else{
                list.negative = true;
                temp = temp.substring(1,temp.length());
            }
        }
    
        for(int i = 0; i < temp.length(); i++) {
            
            if(temp.length() == 1 && temp.charAt(i) == '0') {
                list.numDigits = 0;
                return new BigInteger();
            }
            else if (temp.charAt(i) != '0' && temp.charAt(i) != '1' && temp.charAt(i) != '2' && temp.charAt(i) != '3' 
                    && temp.charAt(i) != '4' && temp.charAt(i) != '5' && temp.charAt(i) != '6' && temp.charAt(i) != '7' 
                    && temp.charAt(i) != '8' && temp.charAt(i) != '9') {
                throw new IllegalArgumentException();
            }
            else if(temp.charAt(i) == '0' && leadingZero == true) {

            }
            else if(temp.substring(i, i+1) != "0" && leadingZero == true){
                leadingZero = false;
                list.front = new DigitNode(Character.getNumericValue(temp.charAt(i)),list.front);
                counter = counter + 1;
            }
            else if(temp.substring(i, i+1) != "0" && leadingZero == false){
                DigitNode Front2 = new DigitNode(Character.getNumericValue(temp.charAt(i)), list.front);
                list.front = Front2;
                counter = counter + 1;
            }
            else if(temp.charAt(i) == ' ' && leadingZero == false){
                throw new IllegalArgumentException();
            }                
        }        
        
        // following line is a placeholder for compilation
        list.numDigits = counter;
        return list;
        
    }
    
    

    /**
     * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
     * DOES NOT MODIFY the input big integers.
     * 
     * NOTE that either or both of the input big integers could be negative.
     * (Which means this method can effectively subtract as well.)
     * 
     * @param first First big integer
     * @param second Second big integer
     * @return Result big integer
     */
    public static BigInteger add(BigInteger first, BigInteger second) {
        
        /* IMPLEMENT THIS METHOD */
        
        BigInteger addition = new BigInteger();
        
        DigitNode a = first.front;
        DigitNode b = second.front;
        DigitNode ptr = null;
        int counter = 0;
        
        int carry = 0;
        int sum;
        
        if(first.negative == true && second.negative == true) {
            addition.negative = true;
        } 
        
        //for positive numbers / double negative numbers
        if(first.negative == true && second.negative == true || first.negative == false && second.negative == false) {
            while(a != null || b != null || carry > 0) {
                sum = carry;
                if(a != null) {
                    sum = sum + a.digit;
                    a = a.next;
                }
                if(b != null) {
                    sum = sum + b.digit;
                    b = b.next;
                }
                if(sum > 9) {
                    carry = 1;
                    sum = sum % 10;
                }
                else {
                    carry = 0;
                }
                if(addition.front == null) {
                    addition.front = new DigitNode(sum, null);
                    ptr = addition.front;
                    counter = counter + 1;
                }
                else {
                    ptr.next = new DigitNode(sum, null);
                    ptr = ptr.next;
                    counter = counter + 1;
                }    
            }
        }
        
        addition.numDigits = counter;
        
        //1 negative 1 positive
        int sub = 0;
        boolean borrow = false;
        boolean aBigger = true;
        boolean same = true;
        
        BigInteger one = first;
        BigInteger two = second;
                

        if(first.negative == false && second.negative == true || first.negative == true && second.negative == false) {    
            
            // figuring out which one is bigger
            if(first.numDigits > second.numDigits) {
                aBigger = true;
                same = false;
            }
            else if(first.numDigits < second.numDigits) {
                aBigger = false;
                same = false;
            }
            else if(first.numDigits == second.numDigits) {
                while(a != null && b != null) {
                    if(a.digit > b.digit) {
                        
                        aBigger = true;
                        a = a.next;
                        b = b.next;
                        same = false;
                    }
                    else if (a.digit < b.digit) {
                        
                        aBigger = false;
                        a = a.next;
                        b = b.next;
                        same = false;
                    }
                    else if(a.digit == b.digit) {
                        a = a.next;
                        b = b.next;
                        
                    }
                }    
            }
            
            a = one.front;
            b = two.front;
            
            // determine which is negative
            if(first.negative == true && second.negative == false && aBigger == true) {
                addition.negative = true;
            }
            else if(first.negative == false && second.negative == true && aBigger == false) {
                addition.negative = true;
            }
            
            //subtraction begins
            if(same == true) {
                addition.front = new DigitNode(0, null);
            }
            else if(aBigger == true) {
                
                while(a != null || b != null) {
                    if(borrow == true) {
                        a.digit = a.digit - 1;
                        borrow = false;
                    }
                    if(a != null && b != null && a.digit < b.digit) {
                        borrow = true;
                        a.digit = a.digit + 10;
                    }
                    else if(a.digit < 0) {
                        a.digit = a.digit + 10;
                        borrow = true;
                    }
                    
                    if(a != null && b == null) {
                        sub = a.digit;
                        a = a.next;
                    }    
                    else if(a != null && b != null){
                        sub = a.digit - b.digit;
                        b = b.next;
                        a = a.next;
                    }
                    
                    if(addition.front == null) {
                        addition.front = new DigitNode(sub, null);
                        ptr = addition.front;
                        counter = counter + 1;
                    }
                    else {
                        ptr.next = new DigitNode(sub, null);
                        ptr = ptr.next;
                        counter = counter + 1;
                    }    
                        
                }
            }
            else if(aBigger == false) {
                
                while(a != null || b != null) {
                    if(borrow == true) {
                        b.digit = b.digit - 1;
                        borrow = false;
                    }
                    if(a != null && b != null && b.digit < a.digit) {
                        borrow = true;
                        b.digit = b.digit + 10;
                    }
                    else if(b.digit < 0) {
                        b.digit = b.digit + 10;
                        borrow = true;
                    }
                    
                    if(a == null && b != null) {
                        sub = b.digit;
                        b = b.next;
                    }    
                    else if(a != null && b != null){
                        sub = b.digit - a.digit;
                        b = b.next;
                        a = a.next;
                    }
                    
                    if(addition.front == null) {
                        addition.front = new DigitNode(sub, null);
                        ptr = addition.front;
                        counter = counter + 1;
                    }
                    else {
                        ptr.next = new DigitNode(sub, null);
                        ptr = ptr.next;
                        counter = counter + 1;
                    }    
                        
                }
            }
            
            String x = addition.toString();
            if (addition.negative == true) {
                x = x.substring(1, x.length());
            }
            
            boolean lookForZero = true;
            
            for(int i = 0; i < x.length() ; i++) {
                if(x.charAt(i) == '0' && lookForZero == true) {
                    counter = counter - 1;
                }
                else if (x.charAt(i) != '0'){
                    lookForZero = false;
                }
            }
            
            addition.numDigits = counter;
            if(same == true) {
                addition.numDigits = 0;
                addition.front = null;
            }
        }
        // following line is a placeholder for compilation
        
        return parse(addition.toString());
    }
    
    /**
     * Returns the BigInteger obtained by multiplying the first big integer
     * with the second big integer
     * 
     * This method DOES NOT MODIFY either of the input big integers
     * 
     * @param first First big integer
     * @param second Second big integer
     * @return A new BigInteger which is the product of the first and second big integers
     */
    public static BigInteger multiply(BigInteger first, BigInteger second) {
        
        /* IMPLEMENT THIS METHOD */
        
        BigInteger product = new BigInteger();
        BigInteger endResult = new BigInteger();
        
        BigInteger a = new BigInteger();
        BigInteger b = new BigInteger();
        
        BigInteger result = new BigInteger();
        
        String m, n;
        
        //converting to string
        BigInteger temp = first;
        BigInteger temp2 = second;
        n = temp.toString();
        m = temp2.toString();
        
        //getting rid of the negative sign in the String
                if (first.negative == true) {
                    n = n.substring(1, n.length());
                } 
                if (second.negative == true) {
                    m = m.substring(1, m.length());
                }
                
        String flipIt = "";
        String z;
        
        int num;
        int num2;
        int carry = 0;
        int counter = 0;
        int multiply;
        int lastNum;
        int count = 0;
        
        //Determine if negative or positive
        if(first.negative == true && second.negative == true){
            product.negative = false;
        }
        else if(first.negative == false && second.negative == false){
            product.negative = false;
        }
        else if(first.negative == true && second.negative == false){
            product.negative = true;
        }
        else if(first.negative == false && second.negative == true){
            product.negative = true;
        }
        
        //Multiply begins here
        for(int j = temp2.numDigits -1; j > -1; j--) {
            carry = 0;
            for(int i = temp.numDigits - 1; i > -1; i--) {
                
                a = parse(m.substring(j,j+1));
                b = parse(n.substring(i,i+1));
                
                if(a.front != null) {
                    num2 = a.front.digit;
                } 
                else{
                    num2 = 0;
                }
                
                if(b.front != null){
                    num = b.front.digit;
                } 
                else{
                    num = 0;
                }
                    
                multiply = num * num2 + carry;  //multiplying the 2 digits
                lastNum = multiply % 10; //the last digit number of the product will be store
                
                result.front = new DigitNode(lastNum, result.front);
                carry = multiply / 10; //the carry that will be added onto the next digit
                count = count + 1;
            }
            
            if(carry != 0) {
                result.front = new DigitNode(carry, result.front);
                count = count + 1;
            }
            
            z = result.toString();
            
            for(int x = z.length() - 1; x > -1; x--) {
                flipIt = flipIt + z.charAt(x);
            }
            
            result = parse(flipIt);
            
            for(int y = 0; y < counter; y++) {
                result.front = new DigitNode(0, result.front);
                count = count + 1;
            }
            
            result.numDigits = count;
            flipIt = "";
            counter = counter + 1;
            endResult = add(endResult, result);
            
            
            result = new BigInteger();
        }
        product.front = endResult.front;
        product.numDigits = endResult.numDigits;   
        
        return product;
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (front == null) {
            return "0";
        }
        String retval = front.digit + "";
        for (DigitNode curr = front.next; curr != null; curr = curr.next) {
                retval = curr.digit + retval;
        }
        
        if (negative) {
            retval = '-' + retval;
        }
        return retval;
    }
}