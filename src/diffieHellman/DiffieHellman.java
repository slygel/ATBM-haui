package diffieHellman;

public class DiffieHellman {
	static long power(long a, long b, long P) {
        if (b == 1) return a;
        else return (long) (Math.pow(a, b) % P);
    }

    public static void main(String[] args) {
        long P, G, x, a, y, b, ka, kb;

        // Cả hai người sẽ đồng ý về các khóa công khai G và P
        P = 23; // Số nguyên tố P được chọn
        System.out.println("The value of P : " + P);

        G = 9; // Một số gốc nguyên cho P, G được chọn
        System.out.println("The value of G : " + G);

        // Alice chọn khóa riêng a
        a = 4; // Khóa riêng được chọn cho Alice
        System.out.println("The private key a for Alice : " + a);

        x = power(G, a, P); // Lấy khóa được tạo ra

        // Bob sẽ chọn khóa riêng b
        b = 3; // b là khóa riêng được chọn
        System.out.println("The private key b for Bob : " + b);
        y = power(G, b, P); // Lấy khóa được tạo ra

        // Tạo khóa bí mật sau khi trao đổi
        ka = power(y, a, P); // Khóa bí mật cho Alice
        kb = power(x, b, P); // Khóa bí mật cho Bob
        System.out.println("Secret key for the Alice is : " + ka);
        System.out.println("Secret key for the Bob is : " + kb);
    }
}
