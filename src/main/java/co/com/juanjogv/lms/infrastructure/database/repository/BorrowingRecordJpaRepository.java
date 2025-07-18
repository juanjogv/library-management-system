package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowingRecordJpaRepository extends JpaRepository<BorrowingRecord, UUID> {}
