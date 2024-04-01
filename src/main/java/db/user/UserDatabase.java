package db.user;

import java.util.List;
import model.User;

public interface UserDatabase {
    /**
     * 새로운 사용자를 데이터베이스에 추가합니다.
     *
     * @param user 데이터베이스에 추가할 사용자 객체.
     */
    void addUser(User user);

    /**
     * 주어진 로그인 ID에 해당하는 사용자를 데이터베이스에서 찾아 반환합니다.
     *
     * @param loginId 찾고자 하는 사용자의 로그인 ID.
     * @return 로그인 ID에 해당하는 사용자 객체. 해당 사용자가 없을 경우 null을 반환합니다.
     */
    User findUserByLoginId(String loginId);

    /**
     * 데이터베이스에 저장된 모든 사용자의 목록을 반환합니다.
     *
     * @return 저장된 모든 사용자를 담고 있는 리스트. 저장된 사용자가 없을 경우 비어 있는 리스트를 반환합니다.
     */
    List<User> findAll();

    /**
     * 데이터베이스의 모든 사용자 정보를 초기화합니다. 이 메소드는 데이터베이스 내의 "user" 테이블의 모든 레코드를 삭제하고, ID 시퀀스를 재시작합니다.
     */
    void clear();
}
