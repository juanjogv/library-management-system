package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.BorrowingRecord;
import co.com.juanjogv.lms.domain.repository.BorrowingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BorrowingRecordRepositoryImpl implements BorrowingRecordRepository {

    private final BorrowingRecordJpaRepository borrowingRecordJpaRepository;

    @Override
    public void saveAll(List<BorrowingRecord> borrowingRecords) {
        borrowingRecordJpaRepository.saveAll(borrowingRecords);
    }
}
