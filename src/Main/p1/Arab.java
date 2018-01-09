package p1;

public class Arab {

    public static String convert(String s){
        if(s == null) return null;
        if(isNumber(s.charAt(0))) return s;
        int number = 0;
        int i=0;
        int lastLetter = 2000000;
        for(;i<s.length();i++){
            if(isLetter(s.charAt(i))) break;
            if(lastLetter >= intAt(s.charAt(i))){
                number += intAt(s.charAt(i));
            }else{
                number = number + intAt(s.charAt(i)) - 2*lastLetter;
            }
            lastLetter = intAt(s.charAt(i));
        }
        String result = Integer.toString(number);
        for(;i<s.length();i++)
            result = result + s.toLowerCase().charAt(i);
        return result;
    }

    public static int intAt(char znak){
        switch (znak){
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
        }
        return 0;
    }

    public static boolean isLetter(char i){
        return  (i >= 'a' && i <= 'z') || (i == 'A');
    }

    public static boolean isNumber(char i){
        return i >=48 && i <= 57;
    }
}
