// "Unwrap 'else' branch" "true"

class T {
    void f(boolean b) {
        if (b)
            throw new RuntimeException("When true");
        System.out.println("Otherwise");
    }
}