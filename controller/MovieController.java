package controller;

import java.util.ArrayList;

import model.MovieDTO;

public class MovieController {
    private ArrayList<MovieDTO> list;
    private int nextId;

    public MovieController() {
        list = new ArrayList<>();
        nextId = 1;

        MovieDTO m = new MovieDTO();
        m.setTitle("영화1");
        m.setSummary("영화1의 줄거리");
        m.setFilmGrade(13);
        insert(m);

        m = new MovieDTO();
        m.setTitle("영화2");
        m.setSummary("영화2의 줄거리");
        m.setFilmGrade(15);
        insert(m);

        m = new MovieDTO();
        m.setTitle("영화3");
        m.setSummary("영화3의 줄거리");
        m.setFilmGrade(18);
        insert(m);
    }

    // 영화 추가 메소드
    public void insert(MovieDTO m) {
        m.setId(nextId++);

        list.add(m);
    }

    // 영화 목록 불러오기 메소드
    public ArrayList<MovieDTO> selectAll() {
        ArrayList<MovieDTO> temp = new ArrayList<>();
        for (MovieDTO m : list) {
            temp.add(new MovieDTO(m));
        }

        return temp;
    }

    // 개별 영화 불러오기 메소드
    public MovieDTO selectOne(int id) {
        for (MovieDTO m : list) {
            if (m.getId() == id) {
                return new MovieDTO(m);
            }
        }

        return null;
    }

    // 영화 수정 메소드
    public void update(MovieDTO m) {
        int index = list.indexOf(m);
        list.set(index, m);
    }

    // 영화 삭제 메소드
    public void delete(int id) {
        MovieDTO m = new MovieDTO();
        m.setId(id);

        list.remove(m);
    }

}
