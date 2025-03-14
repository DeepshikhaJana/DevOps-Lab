public class HelloJenkins {
    public String getMessage() {
        return "Hello, Jenkins!";
    }

    public static void main(String[] args) {
        HelloJenkins hello = new HelloJenkins();
        System.out.println(hello.getMessage());
    }
}