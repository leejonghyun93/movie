package viewer;

import java.util.Scanner;

import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class UserViewer {
    private UserController userController;
    private Scanner scanner;
    private UserDTO logIn;
    private MovieViewer movieViewer;
    private TheaterViewer theaterViewer;
    private ShowViewer showViewer;
    private RatingViewer ratingViewer;

    private final int RANK_ADMIN = 1;
    private final int RANK_CRITIC = 2;

    // 필드를 초기화할 생성자
    public UserViewer() {
        userController = new UserController();
        scanner = new Scanner(System.in);
    }

    // 의존성 주입을 위한 셋터
    public void setMovieViewer(MovieViewer movieViewer) {
        this.movieViewer = movieViewer;
        this.movieViewer.setScanner(scanner);
    }

    public void setTheaterViewer(TheaterViewer theaterViewer) {
        this.theaterViewer = theaterViewer;
    }

    public void setShowViewer(ShowViewer showViewer) {
        this.showViewer = showViewer;
    }

    public void setRatingViewer(RatingViewer ratingViewer) {
        this.ratingViewer = ratingViewer;
        this.ratingViewer.setScanner(scanner);
    }

    // 인덱스 화면
    public void showIndex() {
        String message = new String("1. 회원가입 2. 로그인 3. 종료");
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                // 회원 가입 메소드 실행
                register();
            } else if (userChoice == 2) {
                // 로그인 메소드 실행
                logIn();

                if (logIn != null) {
                    movieViewer.setLogIn(logIn);
                    ratingViewer.setLogIn(logIn);
                    
                    if (logIn.getRank() == RANK_ADMIN) {
                        // 관리자 메뉴 실행
                        showAdminMenu();
                    } else {
                        // 비관리자 메뉴 실행
                        showNonAdminMenu();
                    }
                }
            } else if (userChoice == 3) {
                // 메시지 출력후 종료
                System.out.println("사용해주셔서 감사합니다.");
                scanner.close();
                break;
            }
        }
    }

    private void register() {
        String message;

        message = new String("사용하실 아이디를 입력해주세요.");
        String username = ScannerUtil.nextLine(scanner, message);

        while (userController.validate(username)) {
            System.out.println("해당 아이디는 사용하실 수 없습니다.");
            message = new String("사용하실 아이디를 입력하시거나 뒤로 가실려면 X를 입력해주세요.");
            username = ScannerUtil.nextLine(scanner, message);

            if (username.equalsIgnoreCase("X")) {
                break;
            }
        }

        if (!username.equalsIgnoreCase("X")) {
            message = new String("사용하실 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);

            message = new String("사용하실 닉네임을 입력해주세요.");
            String nickname = ScannerUtil.nextLine(scanner, message);

            UserDTO u = new UserDTO();
            u.setUsername(username);
            u.setPassword(password);
            u.setNickname(nickname);

            userController.insert(u);
        }

    }

    private void logIn() {
        String message;

        message = new String("아이디를 입력해주세요.");
        String username = ScannerUtil.nextLine(scanner, message);

        message = new String("비밀번호를 입력해주세요.");
        String password = ScannerUtil.nextLine(scanner, message);

        UserDTO u = userController.auth(username, password);

        while (u == null) {
            System.out.println("로그인 정보를 다시 확인해주세요.");
            message = new String("아이디를 입력하시거나 뒤로 가실려면 X를 입력해주세요.");
            username = ScannerUtil.nextLine(scanner, message);

            if (username.equalsIgnoreCase("X")) {
                break;
            }

            message = new String("비밀번호를 입력해주세요.");
            password = ScannerUtil.nextLine(scanner, message);
            u = userController.auth(username, password);
        }

        logIn = u;

    }

    // 관리자용 메인화면
    private void showAdminMenu() {
        String message = new String("1. 영화 관리 2. 극장 관리 3. 상영 정보 관리 4. 로그아웃");

        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);

            if (userChoice == 1) {
                // MovieViewer의 showAdminMenu() 실행
                movieViewer.showAdminMenu();
            } else if (userChoice == 2) {
                // TheaterViewer의 showAdminMenu() 실행
                theaterViewer.showAdminMenu();
            } else if (userChoice == 3) {
                // ShowViewer의 showAdminMenu() 실행
                showViewer.showAdminMenu();
            } else {
                System.out.println("로그아웃되셨습니다.");
                logIn = null;
                break;
            }
        }

    }

    // 비관리자용 메인화면
    private void showNonAdminMenu() {
        String message = new String("1. 영화 목록보기 2. 극장 목록보기 3. 내 정보 보기 4. 로그아웃");

        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);

            if (userChoice == 1) {
                // MovieViewer의 printList() 실행
                movieViewer.printList();
            } else if (userChoice == 2) {
                // TheaterViewer의 printList() 실행
                theaterViewer.printList();
            } else if (userChoice == 3) {
                // 개별 회원 정보 보기 메소드 실행
                printOne(logIn.getId());
            } else if (userChoice == 4) {
                System.out.println("로그아웃 되셨습니다.");
                logIn = null;
            }

            if (logIn == null) {
                break;
            }
        }
    }

    private void printOne(int id) {
        UserDTO u = userController.selectOne(id);

        String rank;

        if (u.getRank() == RANK_ADMIN) {
            rank = new String("관리자");
        } else if (u.getRank() == RANK_CRITIC) {
            rank = new String("평론가");
        } else {
            rank = new String("일반 회원");
        }

        System.out.println("===============================");
        System.out.println(u.getNickname() + "님의 정보");
        System.out.println("===============================");
        System.out.println("아이디: " + u.getUsername());
        System.out.println("닉네임: " + u.getNickname());
        System.out.println("회원 등급: " + rank);
        System.out.println("===============================");

        String message = new String("1. 회원 정보 수정 2. 회원 탈퇴 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);

        if (userChoice == 1) {
            // 회원 정보 수정 메소드 실행
            update(id);
        } else if (userChoice == 2) {
            // 회원 탈퇴 메소드 실행
            withdrawl(id);
        }
    }

    private void update(int id) {
        UserDTO u = userController.selectOne(id);

        String message;

        message = new String("새로운 비밀번호를 입력해주세요.");
        u.setPassword(ScannerUtil.nextLine(scanner, message));

        message = new String("새로운 닉네임을 입력해주세요.");
        u.setNickname(ScannerUtil.nextLine(scanner, message));

        userController.update(u);
        printOne(id);
    }

    private void withdrawl(int id) {
        String message = new String("정말로 탈퇴하시겠습니까? Y/N");
        String yesNo = ScannerUtil.nextLine(scanner, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            userController.delete(id);
            logIn = null;
            // RatingViewer의 deleteByWriterId() 실행
            ratingViewer.deleteByWriterId(id);
            System.out.println("회원 탈퇴가 완료되었습니다.");
        } else {
            printOne(id);
        }
    }

    // 해당 회원 번호를 가진 회원의
    // 닉네임만 출력하는 메소드
    public void printNicknameById(int id) {
        UserDTO u = userController.selectOne(id);
        System.out.print(u.getNickname());
    }
    
    // 해당 회원 번호를 가진 회원의
    // 회원 등급을 리턴하는 메소드
    public int selectRankById(int id) {
        return userController.selectOne(id).getRank();
    }
}






















