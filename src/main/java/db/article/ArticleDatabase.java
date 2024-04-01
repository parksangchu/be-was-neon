package db.article;

import java.util.List;
import model.Article;

/**
 * 서버의 Article을 관리하는 데이터베이스입니다.
 */
public interface ArticleDatabase {
    /**
     * 아티클을 데이터베이스에 추가합니다.
     *
     * @param article 추가하고자 하는 아티클의 인스턴스입니다.
     */
    void addArticle(Article article);

    /**
     * aid와 일치하는 특정 아티클을 찾습니다.
     *
     * @param sequenceId 찾고자 하는 아티클의 시퀀스 ID입니다.
     * @return 주어진 시퀀스 ID와 일치하는 아티클을 반환합니다. 해당 아티클이 존재하지 않을 경우 null을 반환합니다.
     */
    Article findArticleBySequenceId(Long sequenceId);

    /**
     * 데이터베이스에 저장된 모든 아티클을 찾습니다.
     *
     * @return 데이터 베이스에 존재하는 모든 아티클을 리스트로 반환합니다. 데이터베이스에 아티클이 없다면 빈 리스트를 반환합니다.
     */
    List<Article> findAll();

    /**
     * 데이터베이스의 모든 아티클을 삭제하고 sequenceId를 1로 초기화합니다.
     */
    void clear();

    /**
     * 데이터베이스의 첫번째 id를 찾습니다.
     *
     * @return 아티클이 존재한다면 아티클의 첫번째 id를, 존재하지 않는다면 null을 반환합니다.
     */
    Long getFirstId();

    /**
     * 데이터베이스의 마지막 id를 찾습니다.
     *
     * @return 아티클이 존재한다면 아티클의 마지막 id를, 존재하지 않는다면 null을 반환합니다.
     */
    Long getRecentId();

}
