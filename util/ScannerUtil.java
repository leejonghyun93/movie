package util;

// Scanner 클래스 변수로
// 키보드 입력을 받을 때
// 도움이 될 메소드를 모아둔 클래스
import java.util.Scanner;

public class ScannerUtil {
    // 1. 입력시 입력 안내 문구 출력을 담당할
    // printMessage()
    private static void printMessage(String message) {
        System.out.println(message);
        System.out.print("> ");
    }

    // 2. int 입력을 담당할 nextInt()
    public static int nextInt(Scanner scanner, String message) {        
        // 1. 사용자의 입력을 String으로 저장한다.
        String temp = nextLine(scanner, message);        
        // 2. 사용자의 입력값이 정수의 형태일때까지 다시 입력을 받는다.
        //    스트링의 값이 특정 형태(=패턴) 인지를 확인할 때에는
        //    우리가 정규표현식(Regular Expression or RegExp) 이라는 것을 사용하게 된다.
        //    정규표현식을 사용하면 문자값의 패턴을 다양하게 검사가능하다.(예시: 유효한 이메일 주소 형태, 대소문자 특수문자가 들어간 비밀번호 등등)
        //    숫자를 검사해야할 경우에는
        //    ^[0-9]+$ 라고 적어주게 된다.
        //    ^: 문자열의 시작
        //    [0-9]: 어떤 글자의 패턴인지 지정. 지금은 0부터 9까지의 값만 넣겠다 라는 의미가 됨
        //    +: 글자의 갯수. + 라고 할 시에는 1개 이상을 뜻하게 됨
        //    $: 문자열의 끝
        String pattern = "^[0-9]+$";
        while(!temp.matches(pattern)) {
            System.out.println("숫자만 입력하실 수 있습니다.");
            temp = nextLine(scanner, message);
        }
        // 3. 사용자의 입력값을 String 에서 int로 변환한다.
        //    특정 데이터타입의 값을 다른 값으로 바꿀 때에는
        //    명시적 형변환을 사용해도 되지만, 
        //    parse 라는 변환 메소드를 실행시킬 수도 있다.
        //    지금의 경우에는 Integer 클래스의 static 메소드인 parseInt() 를 실행하면 된다.
        int result = Integer.parseInt(temp);
        
        // 4. 변환된 int를 리턴한다.
        return result;
    }

    // 3. 특정 범위의 int 입력을 담당할 nextInt()
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int num = nextInt(scanner, message);

        while (!(num >= min && num <= max)) {
            System.out.println("===============================");
            System.out.println("잘못 입력하셨습니다.");
            System.out.println("===============================\n");
            num = nextInt(scanner, message);
        }

        return num;
    }

    // 4. 스캐너 버퍼메모리 버그를 내부적으로 해결하는
    // nextLine()
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);
        String temp = scanner.nextLine();

        // String 클래스 변수의 isEmpty() 메소드는
        // 해당 변수의 저장된 값이 아무것도 없으면 true,
        // 값이 있으면 false를 리턴한다.
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }

        return temp;
    }
}
