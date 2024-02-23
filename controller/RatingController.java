package controller;

import java.util.ArrayList;

import model.RatingDTO;
import viewer.UserViewer;

public class RatingController {
    private ArrayList<RatingDTO> list;
    private int nextId;

    public RatingController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    // 평점을 list에 추가하는 메소드
    public void add(RatingDTO r) {
        r.setId(nextId++);

        list.add(r);
    }

    // 특정 영화의 평점 리스트를 리턴하는 메소드
    public ArrayList<RatingDTO> selectByMovieId(int movieId) {
        ArrayList<RatingDTO> temp = new ArrayList<>();

        for (RatingDTO r : list) {
            if (r.getMovieId() == movieId) {
                temp.add(new RatingDTO(r));
            }
        }

        return temp;
    }

    // 특정 회원 등급의 평점 리스트를 리턴하는 메소드
    public ArrayList<RatingDTO> selectByUserRank(UserViewer userViewer, int movieId, int rank) {
        ArrayList<RatingDTO> temp = new ArrayList<>();

        for (RatingDTO r : list) {
            if (r.getMovieId() == movieId && userViewer.selectRankById(r.getWriterId()) == rank) {
                temp.add(new RatingDTO(r));
            }
        }

        return temp;
    }

    // 파라미터로 들어온 리스트의 평점 평균을 계산하여 리턴하는 메소드
    public double calculateAverage(ArrayList<RatingDTO> list) {
        int sum = 0;

        for (RatingDTO r : list) {
            sum += r.getRate();
        }

        return (double) sum / list.size();
    }

    // 특정 영화의 평점을 모두 삭제하는 메소드
    public void deleteByMovieId(int movieId) {
        for (int i = 0; i < list.size(); i++) {
            RatingDTO r = list.get(i);
            if (r.getMovieId() == movieId) {
                list.remove(i);
                i = -1;
            }
        }
    }

    // 특정 작성자의 평점을 모두 삭제하는 메소드
    public void deleteByWriterId(int writerId) {
        for (int i = 0; i < list.size(); i++) {
            RatingDTO r = list.get(i);
            if (r.getWriterId() == writerId) {
                list.remove(i);
                i = -1;
            }
        }
    }

}
