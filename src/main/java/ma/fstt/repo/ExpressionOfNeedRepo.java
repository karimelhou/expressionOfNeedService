package ma.fstt.repo;

import ma.fstt.entity.ExpressionOfNeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressionOfNeedRepo extends JpaRepository<ExpressionOfNeedEntity,Long> {
    @Query("SELECT a FROM ExpressionOfNeedEntity a WHERE a.userId = :userId")
    List<ExpressionOfNeedEntity> findByUserId(@Param("userId") Long userId);
}
