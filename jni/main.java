
class Main{
    static{
        System.loadLibrary("xxx");
    }
    public static native String stringFromJNI();

    public static void main(String[] args) {
        // Your code here
        System.out.println(stringFromJNI());
    }
}