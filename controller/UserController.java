package controller;

import java.util.ArrayList;

import model.UserDTO;

public class UserController {
    private ArrayList<UserDTO> list;
    private int nextId;

    private final int RANK_ADMIN = 1;
    private final int RANK_CRITIC = 2;
    private final int RANK_GENERAL = 3;

    // 생성자
    public UserController() {
        list = new ArrayList<>();
        nextId = 1;
        
        UserDTO u1 = new UserDTO();
        u1.setId(nextId++);
        u1.setUsername("a1");
        u1.setPassword("1");
        u1.setNickname("관리자");
        u1.setRank(RANK_ADMIN);
        list.add(u1);
        
        u1 = new UserDTO();
        u1.setId(nextId++);
        u1.setUsername("c1");
        u1.setPassword("1");
        u1.setNickname("평론가");
        u1.setRank(RANK_CRITIC);
        list.add(u1);
        
        u1 = new UserDTO();
        u1.setId(nextId++);
        u1.setUsername("g1");
        u1.setPassword("1");
        u1.setNickname("일반회원 1");
        u1.setRank(RANK_GENERAL);
        list.add(u1);
    }

    // 입력 메소드
    public void insert(UserDTO u) {
        u.setId(nextId++);
        u.setRank(RANK_GENERAL);

        list.add(u);
    }

    // 개별 회원을 불러오는 메소드
    public UserDTO selectOne(int id) {
        for (UserDTO u : list) {
            if (u.getId() == id) {
                return new UserDTO(u);
            }
        }

        return null;
    }

    // 사용자로부터 입력받은 username이 중복이거나 'X'와 일치하면
    // true,
    // 그외는 false가 리턴되는 메소드
    public boolean validate(String username) {
        if (username.equalsIgnoreCase("X")) {
            return true;
        }

        for (UserDTO u : list) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    // 사용자로부터 입력받은 username과 password가 일치하는
    // 회원을 리턴하는 메소드
    public UserDTO auth(String username, String password) {
        for (UserDTO u : list) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return new UserDTO(u);
            }
        }

        return null;
    }

    // 회원 정보 수정 메소드
    public void update(UserDTO u) {
        int index = list.indexOf(u);
        list.set(index, u);
    }

    // 회원 정보 삭제 메소드
    public void delete(int id) {
        UserDTO u = new UserDTO();
        u.setId(id);

        list.remove(u);
    }
}
