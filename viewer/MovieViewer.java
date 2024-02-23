package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.MovieController;
import model.MovieDTO;
import model.UserDTO;
import util.ScannerUtil;

public class MovieViewer {
    private MovieController movieController;
    private Scanner scanner;
    private UserDTO logIn;
    private RatingViewer ratingViewer;

    private final int RANK_ADMIN = 1;
    private final int CATEGORY_ALL = 1;
    private final int CATEGORY_CRITIC = 2;
    private final int CATEGORY_GENERAL = 3;

    public MovieViewer() {
        movieController = new MovieController();
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }

    public void setRatingViewer(RatingViewer ratingViewer) {
        this.ratingViewer = ratingViewer;
    }

    public void showAdminMenu() {
        String message = new String("1. 영화 목록 보기 2. 새로운 영화 추가 3. 뒤로 가기");
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            if (userChoice == 1) {
                printList();
            } else if (userChoice == 2) {
                add();
            } else if (userChoice == 3) {
                System.out.println("메인화면으로 이동합니다.");
                break;
            }
        }
    }

    public void printList() {
        ArrayList<MovieDTO> list = movieController.selectAll();

        if (list.isEmpty()) {
            System.out.println("아직 등록된 영화가 없습니다.");
        } else {
            System.out.println("---------------------------------");
            for (MovieDTO m : list) {
                System.out.printf("%d. %s\n", m.getId(), m.getTitle());
            }
            System.out.println("---------------------------------");

            String message = new String("상세보기할 영화의 번호나 뒤로 가실려면 0을 입력해주세요.");
            int userChoice = ScannerUtil.nextInt(scanner, message);

            while (userChoice != 0 && movieController.selectOne(userChoice) == null) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(scanner, message);
            }

            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int id) {
        MovieDTO m = movieController.selectOne(id);

        System.out.println("------------------------------------------");
        System.out.println("제목: " + m.getTitle());
        System.out.println("등급: " + m.getFilmGrade() + "세 이용가");
        System.out.println("------------------------------------------");
        System.out.println(m.getSummary());
        System.out.println("------------------------------------------");
        System.out.println();

        String message;

        if (logIn.getRank() == RANK_ADMIN) {
            // 로그인한 회원이 관리자이므로
            // 관리자용 코드 실행
            message = new String("1. 수정 2. 삭제 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (userChoice == 1) {
                update(id);
            } else if (userChoice == 2) {
                delete(id);
            } else if (userChoice == 3) {
                printList();
            }

        } else {
            // 로그인한 회원이 관리자가 아니므로
            // 비관리자용 코드 실행

            // 전체평점 출력
            ratingViewer.printList(id, CATEGORY_ALL);

            message = new String("1. 전체 평점 출력 2. 평론가 평점 출력 3. 일반 회원 평점 출력 4. 평점 등록 5. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 5);

            while (userChoice != 5) {
                if (userChoice == 1) {
                    ratingViewer.printList(id, CATEGORY_ALL);
                } else if (userChoice == 2) {
                    ratingViewer.printList(id, CATEGORY_CRITIC);
                } else if (userChoice == 3) {
                    ratingViewer.printList(id, CATEGORY_GENERAL);
                } else if (userChoice == 4) {
                    ratingViewer.add(id);
                    ratingViewer.printList(id, CATEGORY_ALL);
                }

                userChoice = ScannerUtil.nextInt(scanner, message, 1, 5);
            }

            if (userChoice == 5) {
                printList();
            }

        }

    }

    private void update(int id) {
        MovieDTO m = movieController.selectOne(id);

        String message;

        message = new String("새로운 제목을 입력해주세요.");
        m.setTitle(ScannerUtil.nextLine(scanner, message));

        message = new String("새로운 줄거리를 입력해주세요.");
        m.setSummary(ScannerUtil.nextLine(scanner, message));

        message = new String("새로운 등급을 입력해주세요.");
        m.setFilmGrade(ScannerUtil.nextInt(scanner, message));

        movieController.update(m);

        printOne(id);
    }

    private void delete(int id) {
        String message = new String("정말로 삭제하시겠습니까? Y/N");
        String yesNo = ScannerUtil.nextLine(scanner, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            movieController.delete(id);
            printList();
        } else {
            printOne(id);
        }
    }

    private void add() {
        MovieDTO m = new MovieDTO();

        String message;

        message = new String("영화의 제목을 입력해주세요.");
        m.setTitle(ScannerUtil.nextLine(scanner, message));

        message = new String("영화의 줄거리를 입력해주세요.");
        m.setSummary(ScannerUtil.nextLine(scanner, message));

        message = new String("영화의 등급을 입력해주세요.");
        m.setFilmGrade(ScannerUtil.nextInt(scanner, message));

        movieController.insert(m);
    }
}
