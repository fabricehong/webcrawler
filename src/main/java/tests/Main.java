package tests;

public class Main {
    public static void main(String[] args) {
        Args arg = new Args("i#,s%,b", new String[]{"i=2", "s=dfgdfg", "b=false"});
        System.out.println("i=" + arg.getInteger('i'));
        System.out.println("s=" + arg.getString('s'));
        System.out.println("b=" + arg.getBoolean('b'));
    }
}