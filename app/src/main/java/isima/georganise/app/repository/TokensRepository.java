package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends JpaRepository<Token, Long>{
}
