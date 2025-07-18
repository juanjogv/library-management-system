package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.BorrowingRecord;
import co.com.juanjogv.lms.domain.model.BorrowingRecordKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordJpaRepository extends JpaRepository<BorrowingRecord, BorrowingRecordKey> {}
