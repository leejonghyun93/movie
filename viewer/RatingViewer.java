package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.RatingController;
import model.RatingDTO;
import model.UserDTO;
import util.ScannerUtil;

public class RatingViewer {
    private RatingController ratingController;
    private UserViewer userViewer;
    private Scanner scanner;
    private UserDTO logIn;

    private final int CATEGORY_ALL = 1;
    private final int RANK_CRITIC = 2;
    private final int RATING_MIN = 1;
    private final int RATING_MAX = 5;

    public RatingViewer() {
        ratingController = new RatingController();
    }

    public void setUserViewer(UserViewer userViewer) {
        this.userViewer = userViewer;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }

    public void deleteByWriterId(int writerId) {
        ratingController.deleteByWriterId(writerId);
    }

    public void printList(int movieId, int category) {
        ArrayList<RatingDTO> list;

        if (category == CATEGORY_ALL) {
            list = ratingController.selectByMovieId(movieId);
        } else {
            list = ratingController.selectByUserRank(userViewer, movieId, category);
        }

        System.out.println("--------------------------------------------");
        if (list.isEmpty()) {
            System.out.println("해당 영화의 평점은 아직 없습니다.");
        } else {
            System.out.println("평점: " + ratingController.calculateAverage(list));

            for (RatingDTO r : list) {
                printOne(r);
            }
        }
        System.out.println("--------------------------------------------");

    }

    private void printOne(RatingDTO r) {
        // 평론가는 "닉네임: 평론 - 점수" 의 형식으로 출력이 되고
        // 일반 회원은 "닉네임: 점수" 로 출력되게 코드를 작성해보자
        userViewer.printNicknameById(r.getWriterId());

        // printf에서 출력될 글의 형식을 저장한 String 변수
        String format;

        // 만약 파라미터 r의 review 필드가 비어있을 경우엔 일반 회원이므로
        // ": %d\n" 을 format에 저장하고
        // 그 외네는
        // ": %s - %d\n" 을 format에 저장한다.

        if (r.getReview().isEmpty()) {
            format = new String(": %d\n");
            System.out.printf(format, r.getRate());
        } else {
            format = new String(": %s - %d\n");
            System.out.printf(format, r.getReview(), r.getRate());
        }

    }

    public void add(int movieId) {
        RatingDTO r = new RatingDTO();

        r.setMovieId(movieId);
        r.setWriterId(logIn.getId());

        String message;

        message = new String("해당 영화의 평점을 입력해주세요.");
        r.setRate(ScannerUtil.nextInt(scanner, message, RATING_MIN, RATING_MAX));

        if (userViewer.selectRankById(logIn.getId()) == RANK_CRITIC) {
            message = new String("해당 영화의 평론을 입력해주세요.");
            r.setReview(ScannerUtil.nextLine(scanner, message));
        }

        ratingController.add(r);
    }

}
